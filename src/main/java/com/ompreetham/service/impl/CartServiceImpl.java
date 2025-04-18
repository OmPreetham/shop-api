package com.ompreetham.service.impl;

import com.ompreetham.dto.AddToCartRequest;
import com.ompreetham.dto.CartDTO;
import com.ompreetham.dto.RemoveFromCartRequest;
import com.ompreetham.dto.UpdateCartItemRequest;
import com.ompreetham.entity.Cart;
import com.ompreetham.entity.CartItem;
import com.ompreetham.entity.Product;
import com.ompreetham.entity.User;
import com.ompreetham.exception.ResourceNotFoundException;
import com.ompreetham.mapper.CartMapper;
import com.ompreetham.repository.CartItemRepository;
import com.ompreetham.repository.CartRepository;
import com.ompreetham.repository.ProductRepository;
import com.ompreetham.repository.UserRepository;
import com.ompreetham.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                           UserRepository userRepository, ProductRepository productRepository,
                           CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCartByUserId(Long userId) {
        // Find user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Get or create cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(user));
        
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO addToCart(Long userId, AddToCartRequest request) {
        // Find user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Find product or throw exception
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));
        
        // Check if product is in stock
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available for product: " + product.getName());
        }
        
        // Get or create cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(user));
        
        // Check if product already exists in cart
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());
        
        if (existingItemOpt.isPresent()) {
            // Update existing item
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // Create new cart item
            CartItem newCartItem = new CartItem(cart, product, request.getQuantity());
            cart.addCartItem(newCartItem);
        }
        
        // Update cart timestamp
        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        
        return cartMapper.toDTO(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(Long userId, UpdateCartItemRequest request) {
        // Find user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Get cart or throw exception
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));
        
        // Find cart item or throw exception
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", request.getCartItemId()));
        
        // Check if cart item belongs to the user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to the user's cart");
        }
        
        // Check if product is in stock
        Product product = cartItem.getProduct();
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available for product: " + product.getName());
        }
        
        // Update cart item quantity
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        
        // Update cart timestamp
        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        
        return cartMapper.toDTO(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO removeFromCart(Long userId, RemoveFromCartRequest request) {
        // Find user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Get cart or throw exception
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));
        
        // Find cart item or throw exception
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", request.getCartItemId()));
        
        // Check if cart item belongs to the user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to the user's cart");
        }
        
        // Remove cart item
        cart.removeCartItem(cartItem);
        cartItemRepository.delete(cartItem);
        
        // Update cart timestamp
        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        
        return cartMapper.toDTO(updatedCart);
    }
    
    // Helper method to create a new cart for a user
    private Cart createNewCart(User user) {
        Cart newCart = new Cart(user);
        return cartRepository.save(newCart);
    }
} 