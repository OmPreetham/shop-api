package com.ompreetham.entity;

/**
 * Enum representing the various states of an order in the system.
 */
public enum OrderStatus {
    /**
     * Order has been created but not yet confirmed.
     */
    PENDING,
    
    /**
     * Order has been confirmed and is awaiting processing.
     */
    CONFIRMED,
    
    /**
     * Order is being processed (packaging, etc.).
     */
    PROCESSING,
    
    /**
     * Order has been shipped and is in transit.
     */
    SHIPPED,
    
    /**
     * Order has been delivered to the customer.
     */
    DELIVERED,
    
    /**
     * Order has been cancelled by the customer or admin.
     */
    CANCELLED,
    
    /**
     * Order has been returned by the customer.
     */
    RETURNED
} 