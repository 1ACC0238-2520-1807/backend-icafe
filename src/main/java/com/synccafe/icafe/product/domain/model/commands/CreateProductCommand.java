package com.synccafe.icafe.product.domain.model.commands;

import com.synccafe.icafe.product.domain.model.valueobjects.DirectItemSpec;
import com.synccafe.icafe.product.domain.model.valueobjects.ProductType;
import com.synccafe.icafe.product.domain.model.valueobjects.RecipeItem;

import java.util.List;

public record CreateProductCommand(
        Long ownerId,
        Long branchId,
        String name,
        String category,
        ProductType type,
        Integer portions,
        String steps,
        DirectItemSpec directItem,
        List<RecipeItem> components
) {
    public CreateProductCommand {
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException("El ID del propietario debe ser mayor a cero");
        }
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de producto no puede ser nulo");
        }
        if (portions == null || portions <= 0) {
            throw new IllegalArgumentException("Las porciones deben ser mayor a cero");
        }
    }
}