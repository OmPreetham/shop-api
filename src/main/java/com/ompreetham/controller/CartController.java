package com.ompreetham.controller;

import com.ompreetham.dto.AddToCartRequest;
import com.ompreetham.dto.CartDTO;
import com.ompreetham.dto.RemoveFromCartRequest;
import com.ompreetham.dto.UpdateCartItemRequest;
import com.ompreetham.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        CartDTO cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDTO> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddToCartRequest request) {
        CartDTO updatedCart = cartService.addToCart(userId, request);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<CartDTO> updateCartItem(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        CartDTO updatedCart = cartService.updateCartItem(userId, request);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<CartDTO> removeFromCart(
            @PathVariable Long userId,
            @Valid @RequestBody RemoveFromCartRequest request) {
        CartDTO updatedCart = cartService.removeFromCart(userId, request);
        return ResponseEntity.ok(updatedCart);
    }
} 