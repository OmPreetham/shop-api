package com.ompreetham.entity;

/**
 * Enum representing the payment status of an order.
 */
public enum PaymentStatus {
    /**
     * Payment is pending or not initiated.
     */
    PENDING,
    
    /**
     * Payment has been successfully completed.
     */
    COMPLETED,
    
    /**
     * Payment failed due to some error.
     */
    FAILED,
    
    /**
     * Payment was refunded to the customer.
     */
    REFUNDED,
    
    /**
     * Payment is in process but not yet confirmed.
     */
    PROCESSING
} 