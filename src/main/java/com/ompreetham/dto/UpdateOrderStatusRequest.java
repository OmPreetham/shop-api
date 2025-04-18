package com.ompreetham.dto;

import com.ompreetham.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;
    
    private String notes;
    
    // Default constructor
    public UpdateOrderStatusRequest() {
    }
    
    // Constructor with fields
    public UpdateOrderStatusRequest(OrderStatus orderStatus, String notes) {
        this.orderStatus = orderStatus;
        this.notes = notes;
    }
    
    // Getters and Setters
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 