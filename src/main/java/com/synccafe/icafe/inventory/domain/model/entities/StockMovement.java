package com.synccafe.icafe.inventory.domain.model.entities;

import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
public class StockMovement extends AuditableAbstractAggregateRoot<StockMovement> {
    
    @Column(nullable = false)
    private Long productId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;
    
    @Column(nullable = false)
    private Double quantity;
    
    @Column(nullable = false, length = 255)
    private String origin;
    
    @Column(nullable = false)
    private LocalDateTime movementDate;
    
    protected StockMovement() {}
    
    public StockMovement(Long productId, MovementType type, Double quantity, String origin) {
        this();
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.origin = origin;
        this.movementDate = LocalDateTime.now();
        
        validateMovement();
    }
    
    private void validateMovement() {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Movement type cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }
    }
    
    public enum MovementType {
        ENTRADA, SALIDA
    }
}