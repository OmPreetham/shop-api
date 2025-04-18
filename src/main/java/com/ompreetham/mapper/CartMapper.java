package com.ompreetham.mapper;

import com.ompreetham.dto.CartDTO;
import com.ompreetham.dto.CartItemDTO;
import com.ompreetham.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    @Autowired
    public CartMapper(CartItemMapper cartItemMapper) {
        this.cartItemMapper = cartItemMapper;
    }

    public CartDTO toDTO(Cart cart) {
        if (cart == null) {
            return null;
        }
        
        // Map cart items to DTOs
        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(cartItemMapper::toDTO)
                .collect(Collectors.toList());
        
        // Create cart DTO
        CartDTO cartDTO = new CartDTO(
            cart.getId(),
            cart.getUser().getId(),
            cart.getUser().getFirstName() + " " + cart.getUser().getLastName(),
            cartItemDTOs,
            cart.calculateTotal(),
            cart.getUpdatedAt()
        );
        
        return cartDTO;
    }
} 