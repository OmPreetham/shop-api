package com.ompreetham.controller;

import com.ompreetham.dto.OrderDTO;
import com.ompreetham.dto.PlaceOrderRequest;
import com.ompreetham.dto.UpdateOrderStatusRequest;
import com.ompreetham.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}/place")
    public ResponseEntity<OrderDTO> placeOrder(
            @PathVariable Long userId,
            @Valid @RequestBody PlaceOrderRequest request) {
        OrderDTO orderDTO = orderService.placeOrder(userId, request);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{userId}/paginated")
    public ResponseEntity<Map<String, Object>> getUserOrdersPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        
        org.springframework.data.domain.Page<OrderDTO> orderPage = 
                orderService.getUserOrdersPaginated(userId, page, size, sort, direction);
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderDTO orderDTO = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(orderDTO);
    }
} 