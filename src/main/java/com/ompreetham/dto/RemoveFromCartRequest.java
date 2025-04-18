package com.ompreetham.dto;

import jakarta.validation.constraints.NotNull;

public class RemoveFromCartRequest {

    @NotNull(message = "Cart item ID is required")
    private Long cartItemId;

    // Default constructor
    public RemoveFromCartRequest() {
    }

    // Constructor with fields
    public RemoveFromCartRequest(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }
} 