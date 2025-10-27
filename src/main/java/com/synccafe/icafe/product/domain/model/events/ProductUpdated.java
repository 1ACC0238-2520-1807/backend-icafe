package com.synccafe.icafe.product.domain.model.events;

import com.synccafe.icafe.shared.domain.model.events.DomainEvent;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record ProductUpdated(
        Long productId,
        Long ownerId,
        Long branchId,
        String name,
        Set<String> changedFields,
        Integer version,
        DirectItemSpec directItem,
        List<RecipeItem> components,
        Instant occurredOn
) implements DomainEvent {

    public ProductUpdated(Long productId, Long ownerId, Long branchId, String name,
                         Set<String> changedFields, Integer version, 
                         DirectItemSpec directItem, List<RecipeItem> components) {
        this(productId, ownerId, branchId, name, changedFields, version, 
             directItem, components, Instant.now());
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}