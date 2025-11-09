package com.synccafe.icafe.purchasing.domain.model.events;

import com.synccafe.icafe.shared.domain.model.events.DomainEvent;

import java.time.Instant;
import java.time.LocalDate;

public record PurchaseOrderCreatedEvent(
    Long purchaseOrderId,
    Long branchId,
    Long supplyItemId,
    Double quantity,
    LocalDate purchaseDate,
    Instant occurredOn
) implements DomainEvent {
    
    public PurchaseOrderCreatedEvent {
        if (purchaseOrderId == null) {
            throw new IllegalArgumentException("Purchase Order ID cannot be null");
        }
        if (branchId == null) {
            throw new IllegalArgumentException("Branch ID cannot be null");
        }
        if (supplyItemId == null) {
            throw new IllegalArgumentException("Supply Item ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (purchaseDate == null) {
            throw new IllegalArgumentException("Purchase date cannot be null");
        }
        if (occurredOn == null) {
            throw new IllegalArgumentException("Occurred on timestamp cannot be null");
        }
    }
}