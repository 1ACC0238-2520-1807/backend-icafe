package com.synccafe.icafe.product.domain.model.commands;

import java.util.List;

public record UpdateProductCommand(
    String name,
    double costPrice,
    double profitMargin
) {
    public UpdateProductCommand {
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