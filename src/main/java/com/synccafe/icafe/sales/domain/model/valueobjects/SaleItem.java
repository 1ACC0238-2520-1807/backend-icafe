package com.synccafe.icafe.sales.domain.model.valueobjects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class SaleItem {
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "quantity", nullable = false)
    private Double quantity;
    
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "unit_price"))
    private Money unitPrice;
    
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "subtotal"))
    private Money subtotal;

    protected SaleItem() {}

    public SaleItem(Long productId, Double quantity, Money unitPrice) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID cannot be null or negative");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null) {
            throw new IllegalArgumentException("Unit price cannot be null");
        }
        
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(quantity);
    }

    // Getters
    public Long getProductId() { return productId; }
    public Double getQuantity() { return quantity; }
    public Money getUnitPrice() { return unitPrice; }
    public Money getSubtotal() { return subtotal; }
}