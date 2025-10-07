package com.synccafe.icafe.product.domain.model.commands;

public record ActivateProductCommand(Long productId) {
    public ActivateProductCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a cero");
        }
    }
}