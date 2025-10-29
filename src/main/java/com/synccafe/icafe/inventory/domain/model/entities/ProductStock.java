package com.synccafe.icafe.inventory.domain.model.entities;

import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "product_stocks")
@Getter
public class ProductStock extends AuditableAbstractAggregateRoot<ProductStock> {
    
    @Column(nullable = false, unique = true)
    private Long productId;
    
    @Column(nullable = false)
    private Double currentStock;
    
    protected ProductStock() {}
    
    public ProductStock(Long productId) {
        this();
        this.productId = productId;
        this.currentStock = 0.0;
        
        validateProductStock();
    }
    
    public void addStock(Double quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive");
        }
        this.currentStock += quantity;
    }
    
    public void removeStock(Double quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity to remove must be positive");
        }
        if (this.currentStock < quantity) {
            throw new IllegalArgumentException("Insufficient stock. Current: " + this.currentStock + ", Requested: " + quantity);
        }
        this.currentStock -= quantity;
    }
    
    private void validateProductStock() {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}