package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.CreateProductCommand;
import com.synccafe.icafe.product.domain.model.valueobjects.DirectItemSpec;
import com.synccafe.icafe.product.domain.model.valueobjects.RecipeItem;
import com.synccafe.icafe.product.interfaces.rest.resources.CreateProductResource;

import java.util.List;
import java.util.stream.Collectors;

public class CreateProductCommandFromResourceAssembler {

    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        DirectItemSpec directItem = null;
        if (resource.directItem() != null) {
            directItem = new DirectItemSpec(
                    resource.directItem().itemId(),
                    resource.directItem().quantity()
            );
        }

        List<RecipeItem> components = null;
        if (resource.components() != null) {
            components = resource.components().stream()
                    .map(item -> new RecipeItem(item.itemId(), item.quantity()))
                    .collect(Collectors.toList());
        }

        return new CreateProductCommand(
                resource.ownerId(),
                resource.branchId(),
                resource.name(),
                resource.category(),
                resource.type(),
                resource.portions(),
                resource.steps(),
                directItem,
                components
        );
    }
}