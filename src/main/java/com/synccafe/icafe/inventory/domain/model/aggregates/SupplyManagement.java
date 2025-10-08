package com.synccafe.icafe.inventory.domain.model.aggregates;

import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;
import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class SupplyManagement extends AuditableAbstractAggregateRoot<SupplyManagement> {
    
    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "supplyManagement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SupplyItem> supplyItems = new HashSet<>();

    @OneToMany(mappedBy = "supplyManagement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InventoryTransaction> transactions = new ArrayList<>();

    protected SupplyManagement() {}

    public SupplyManagement(String name) {
        this();
        this.name = name;
    }

    public void addSupplyItem(SupplyItem supplyItem) {
        supplyItem.setSupplyManagement(this);
        this.supplyItems.add(supplyItem);
    }

    public void removeSupplyItem(SupplyItem supplyItem) {
        this.supplyItems.remove(supplyItem);
        supplyItem.setSupplyManagement(null);
    }

    public void registerTransaction(TransactionType type, Quantity quantity, String reference, SupplyItem supplyItem) {
        InventoryTransaction transaction = new InventoryTransaction(type, quantity, reference);
        transaction.setSupplyManagement(this);
        this.transactions.add(transaction);

        // Apply the transaction to the supply item
        switch (type) {
            case ENTRADA -> supplyItem.addQuantity(quantity);
            case SALIDA -> supplyItem.subtractQuantity(quantity);
            case AJUSTE -> supplyItem.updateQuantity(quantity);
        }

        // Check if reorder is needed after the transaction
        if (supplyItem.isLowStock()) {
            // Here you could publish a domain event for low stock
            // registerEvent(new LowStockDetectedEvent(supplyItem.getId()));
        }
    }

    public List<SupplyItem> getLowStockItems() {
        return supplyItems.stream()
                .filter(SupplyItem::isLowStock)
                .toList();
    }

    public SupplyItem findSupplyItemById(Long supplyItemId) {
        return supplyItems.stream()
                .filter(item -> item.getId().equals(supplyItemId))
                .findFirst()
                .orElse(null);
    }
}