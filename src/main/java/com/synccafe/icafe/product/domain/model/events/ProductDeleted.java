package com.synccafe.icafe.product.domain.model.events;

import com.synccafe.icafe.shared.domain.model.events.DomainEvent;

import java.time.Instant;

public record ProductDeleted(
        Long productId,
        Long ownerId,
        Long branchId,
        String name,
        Instant occurredOn
) implements DomainEvent {

    public ProductDeleted(Long productId, Long ownerId, Long branchId, String name) {
        this(productId, ownerId, branchId, name, Instant.now());
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}