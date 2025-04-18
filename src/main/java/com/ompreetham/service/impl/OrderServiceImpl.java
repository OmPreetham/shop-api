package com.ompreetham.service.impl;

import com.ompreetham.dto.OrderDTO;
import com.ompreetham.dto.PlaceOrderRequest;
import com.ompreetham.dto.UpdateOrderStatusRequest;
import com.ompreetham.entity.*;
import com.ompreetham.exception.ResourceNotFoundException;
import com.ompreetham.mapper.OrderMapper;
import com.ompreetham.repository.*;
import com.ompreetham.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                           CartRepository cartRepository, CartItemRepository cartItemRepository,
                           UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDTO placeOrder(Long userId, PlaceOrderRequest request) {
        // Find the user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Find the cart or throw exception
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));
        
        // Check if cart is empty
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with empty cart");
        }
        
        // Create new order
        Order order = new Order(
            user,
            cart.calculateTotal(),
            OrderStatus.PENDING,
            PaymentStatus.PENDING,
            request.getShippingAddress(),
            request.getBillingAddress(),
            request.getPaymentMethod()
        );
        
        if (request.getNotes() != null) {
            order.setNotes(request.getNotes());
        }
        
        // Save the order to get an ID
        Order savedOrder = orderRepository.save(order);
        
        // Convert cart items to order items
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            
            // Create order item
            OrderItem orderItem = new OrderItem(
                savedOrder,
                product,
                cartItem.getQuantity(),
                product.getPrice(),
                product.getName()
            );
            
            // Add the order item to the order
            savedOrder.addOrderItem(orderItem);
            
            // Update product stock quantity
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
        }
        
        // Save the updated order with items
        Order finalOrder = orderRepository.save(savedOrder);
        
        // Clear the cart
        cart.getCartItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        
        return orderMapper.toDTO(finalOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getUserOrders(Long userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        
        // Get all orders for the user ordered by creation date desc
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        
        // Map orders to DTOs
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> getUserOrdersPaginated(Long userId, int page, int size, String sortBy, String direction) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        
        // Create pageable object
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Get page of orders for the user
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        
        // Map orders to DTOs
        return orders.map(orderMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        // Update order status
        order.setOrderStatus(request.getOrderStatus());
        
        // Update notes if provided
        if (request.getNotes() != null) {
            if (order.getNotes() == null) {
                order.setNotes(request.getNotes());
            } else {
                order.setNotes(order.getNotes() + "\n" + request.getNotes());
            }
        }
        
        // Update timestamp
        order.setUpdatedAt(LocalDateTime.now());
        
        // Handle payment status if order status is DELIVERED or CANCELLED
        if (request.getOrderStatus() == OrderStatus.DELIVERED && order.getPaymentStatus() == PaymentStatus.PENDING) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        } else if (request.getOrderStatus() == OrderStatus.CANCELLED && order.getPaymentStatus() == PaymentStatus.PENDING) {
            order.setPaymentStatus(PaymentStatus.FAILED);
        }
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }
    
    // Helper method to create Sort object
    private Sort createSort(String sortBy, String direction) {
        // Default to sorting by createdAt if no valid sort field is provided
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "createdAt";
        }
        
        // Create Sort object based on direction
        if (direction != null && direction.equalsIgnoreCase("asc")) {
            return Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            return Sort.by(Sort.Direction.DESC, sortBy);
        }
    }
} 