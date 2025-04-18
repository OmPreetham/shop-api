package com.ompreetham.mapper;

import com.ompreetham.dto.CartItemDTO;
import com.ompreetham.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartItemMapper {

    public CartItemDTO toDTO(CartItem cartItem) {
        if (cartItem == null || cartItem.getProduct() == null) {
            return null;
        }
        
        CartItemDTO cartItemDTO = new CartItemDTO(
            cartItem.getId(),
            cartItem.getProduct().getId(),
            cartItem.getProduct().getName(),
            cartItem.getProduct().getPrice(),
            cartItem.getQuantity()
        );
        
        // Calculate subtotal
        cartItemDTO.calculateSubtotal();
        
        return cartItemDTO;
    }
} 