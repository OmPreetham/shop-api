package com.ompreetham.service;

import com.ompreetham.dto.AddToCartRequest;
import com.ompreetham.dto.CartDTO;
import com.ompreetham.dto.RemoveFromCartRequest;
import com.ompreetham.dto.UpdateCartItemRequest;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addToCart(Long userId, AddToCartRequest request);
    CartDTO updateCartItem(Long userId, UpdateCartItemRequest request);
    CartDTO removeFromCart(Long userId, RemoveFromCartRequest request);
} 