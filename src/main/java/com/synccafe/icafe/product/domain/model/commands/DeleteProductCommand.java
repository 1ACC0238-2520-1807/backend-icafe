package com.synccafe.icafe.product.domain.model.commands;

public record DeleteProductCommand(Long productId) {
    public DeleteProductCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a cero");
        }
    }
}