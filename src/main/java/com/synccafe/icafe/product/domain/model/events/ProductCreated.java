package com.synccafe.icafe.product.domain.model.events;

import com.synccafe.icafe.shared.domain.model.events.DomainEvent;

import java.time.Instant;
import java.util.List;

public record ProductCreated(
        Long productId,
        Long ownerId,
        Long branchId,
        String name,
        String category,
        ProductType type,
        Integer portions,
        String steps,
        Integer version,
        DirectItemSpec directItem,
        List<RecipeItem> components,
        Instant occurredOn
) implements DomainEvent {

    public ProductCreated(Long productId, Long ownerId, Long branchId, String name, 
                         String category, ProductType type, Integer portions, String steps,
                         Integer version, DirectItemSpec directItem, List<RecipeItem> components) {
        this(productId, ownerId, branchId, name, category, type, portions, steps, 
             version, directItem, components, Instant.now());
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}