package com.synccafe.icafe.product.domain.model.commands;

import com.synccafe.icafe.product.domain.model.valueobjects.DirectItemSpec;
import com.synccafe.icafe.product.domain.model.valueobjects.RecipeItem;

import java.util.List;

public record UpdateProductCommand(
        Long productId,
        String name,
        String category,
        Integer portions,
        String steps,
        DirectItemSpec directItem,
        List<RecipeItem> components
) {
    public UpdateProductCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a cero");
        }
    }
}