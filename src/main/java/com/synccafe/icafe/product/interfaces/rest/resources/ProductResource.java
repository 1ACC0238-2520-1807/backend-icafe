package com.synccafe.icafe.product.interfaces.rest.resources;

import com.synccafe.icafe.product.domain.model.valueobjects.ProductStatus;

import java.util.Date;
import java.util.List;

public record ProductResource(
        Long id,
        Long ownerId,
        Long branchId,
        String name,
        String category,
        ProductType type,
        ProductStatus status,
        Integer portions,
        String steps,
        DirectItemSpecResource directItem,
        List<RecipeItemResource> components,
        Date createdAt,
        Date updatedAt,
        Integer version
) {
    public record DirectItemSpecResource(
            Long itemId,
            Double portionFactor
    ) {}

    public record RecipeItemResource(
            Long itemId,
            Double qtyPerPortion
    ) {}
}