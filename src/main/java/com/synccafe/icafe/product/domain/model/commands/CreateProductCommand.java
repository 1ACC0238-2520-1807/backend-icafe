package com.synccafe.icafe.product.domain.model.commands;

import java.util.List;

public record CreateProductCommand(
    Long branchId,
    String name,
    double costPrice,
    double profitMargin
) {
    public  CreateProductCommand {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (costPrice < 0) {
            throw new IllegalArgumentException("El precio de costo no puede ser negativo");
        }
        if (profitMargin < 0) {
            throw new IllegalArgumentException("El margen de ganancia no puede ser negativo");
        }
    }

}