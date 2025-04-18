package com.ompreetham.mapper;

import com.ompreetham.dto.OrderItemDTO;
import com.ompreetham.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        
        OrderItemDTO orderItemDTO = new OrderItemDTO(
            orderItem.getId(),
            orderItem.getOrder().getId(),
            orderItem.getProduct() != null ? orderItem.getProduct().getId() : null,
            orderItem.getProductName(),
            orderItem.getQuantity(),
            orderItem.getPrice()
        );
        
        return orderItemDTO;
    }
} 