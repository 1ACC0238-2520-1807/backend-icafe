package com.synccafe.icafe.inventory.domain.model.entities;

import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(
    name = "product_stocks",
    uniqueConstraints = @UniqueConstraint(columnNames = {"supply_item_id", "branch_id"})
)
@Getter
public class ProductStock extends AuditableAbstractAggregateRoot<ProductStock> {
    
    @Column(name = "supply_item_id", nullable = false)
    private Long supplyItemId;

    @Embedded
    @AttributeOverride(name = "branchId", column = @Column(name = "branch_id", nullable = false))
    private BranchId branchId;
    
    @Column(nullable = false)
    private Double currentStock;
    
    protected ProductStock() {}
    
    public ProductStock(Long supplyItemId, Long branchId) {
        this();
        this.supplyItemId = supplyItemId;
        this.branchId = new BranchId(branchId);
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
        if (supplyItemId == null) {
            throw new IllegalArgumentException("SupplyItem ID cannot be null");
        }
        if (branchId == null) {
            throw new IllegalArgumentException("Branch ID cannot be null");
        }
    }
}