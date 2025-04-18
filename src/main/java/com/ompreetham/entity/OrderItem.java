package com.ompreetham.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @NotNull(message = "Price is required")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull(message = "Subtotal is required")
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    // Default constructor
    public OrderItem() {
    }

    // Constructor with fields
    public OrderItem(Order order, Product product, Integer quantity, BigDecimal price, String productName) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = price; // Set unit price to the same as price from product
        this.price = price;
        this.productName = productName;
        this.subtotal = calculateSubtotal();
    }

    // Convenience method to calculate subtotal
    public BigDecimal calculateSubtotal() {
        return price != null && quantity != null
            ? price.multiply(BigDecimal.valueOf(quantity))
            : BigDecimal.ZERO;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.subtotal = calculateSubtotal();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + (order != null ? order.getId() : null) +
                ", product=" + (product != null ? product.getId() : null) +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", price=" + price +
                ", productName='" + productName + '\'' +
                ", subtotal=" + subtotal +
                '}';
    }
} 