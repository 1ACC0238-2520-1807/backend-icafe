package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.UpdateProductCommand;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateProductResource;

import java.util.List;

public class UpdateProductCommandFromResourceAssembler {

    public static UpdateProductCommand toCommandFromResource(Long productId, UpdateProductResource resource) {
        DirectItemSpec directItem = resource.directItem() != null ?
                new DirectItemSpec(
                        resource.directItem().itemId(),
                        resource.directItem().portionFactor(),
                        UnitType.UNITS) : null;

        List<RecipeItem> components = resource.components() != null ?
                resource.components().stream()
                        .map(item -> new RecipeItem(item.itemId(), item.qtyPerPortion(), item.unit()))
                        .toList() : null;

        return new UpdateProductCommand(
                productId,
                resource.name(),
                resource.category(),
                resource.portions(),
                resource.steps(),
                directItem,
                components
        );
    }
}