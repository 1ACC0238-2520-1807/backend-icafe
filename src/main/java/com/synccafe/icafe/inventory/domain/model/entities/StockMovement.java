package com.synccafe.icafe.inventory.domain.model.entities;

import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
public class StockMovement extends AuditableAbstractAggregateRoot<StockMovement> {
    
    @Column(nullable = false)
    private Long supplyItemId;

    @Embedded
    @AttributeOverride(name = "branchId", column = @Column(name = "branch_id", nullable = false))
    private BranchId branchId;
    
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
    
    public StockMovement(Long supplyItemId, Long branchId, MovementType type, Double quantity, String origin) {
        this();
        this.supplyItemId = supplyItemId;
        this.branchId = new BranchId(branchId);
        this.type = type;
        this.quantity = quantity;
        this.origin = origin;
        this.movementDate = LocalDateTime.now();
        
        validateMovement();
    }
    
    private void validateMovement() {
        if (supplyItemId == null) {
            throw new IllegalArgumentException("SupplyItem ID cannot be null");
        }
        if (branchId == null) {
            throw new IllegalArgumentException("Branch ID cannot be null");
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