package com.ompreetham.mapper;

import com.ompreetham.dto.OrderDTO;
import com.ompreetham.dto.OrderItemDTO;
import com.ompreetham.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        // Map order items to DTOs
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList());
        
        // Create order DTO
        OrderDTO orderDTO = new OrderDTO(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getFirstName() + " " + order.getUser().getLastName(),
            order.getTotalAmount(),
            order.getOrderStatus(),
            order.getPaymentStatus(),
            order.getShippingAddress(),
            order.getBillingAddress(),
            order.getPaymentMethod(),
            order.getTrackingNumber(),
            order.getNotes(),
            order.getOrderDate(),
            orderItemDTOs,
            order.getCreatedAt(),
            order.getUpdatedAt()
        );
        
        return orderDTO;
    }
} 