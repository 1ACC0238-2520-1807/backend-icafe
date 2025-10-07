package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.UpdateProductCommand;
import com.synccafe.icafe.product.domain.model.valueobjects.DirectItemSpec;
import com.synccafe.icafe.product.domain.model.valueobjects.RecipeItem;
import com.synccafe.icafe.product.domain.model.valueobjects.UnitType;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateProductResource;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateProductCommandFromResourceAssembler {

    public static UpdateProductCommand toCommandFromResource(Long productId, UpdateProductResource resource) {
        DirectItemSpec directItem = null;
        if (resource.directItem() != null) {
            directItem = new DirectItemSpec(
                    resource.directItem().itemId(),
                    resource.directItem().quantity(),
                    UnitType.UNITS  // Default unit type
            );
        }

        List<RecipeItem> components = null;
        if (resource.components() != null) {
            components = resource.components().stream()
                    .map(item -> new RecipeItem(item.itemId(), item.quantity(), UnitType.UNITS))
                    .collect(Collectors.toList());
        }

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