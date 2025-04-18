package com.ompreetham.service;

import com.ompreetham.dto.OrderDTO;
import com.ompreetham.dto.PlaceOrderRequest;
import com.ompreetham.dto.UpdateOrderStatusRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    
    OrderDTO placeOrder(Long userId, PlaceOrderRequest request);
    
    List<OrderDTO> getUserOrders(Long userId);
    
    Page<OrderDTO> getUserOrdersPaginated(Long userId, int page, int size, String sortBy, String direction);
    
    OrderDTO getOrderById(Long orderId);
    
    OrderDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);
} 