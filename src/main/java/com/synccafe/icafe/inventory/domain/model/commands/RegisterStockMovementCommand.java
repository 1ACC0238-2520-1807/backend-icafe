package com.synccafe.icafe.inventory.domain.model.commands;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;

public record RegisterStockMovementCommand(
    Long productId,
    StockMovement.MovementType type,
    Double quantity,
    String origin
) {
    public RegisterStockMovementCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Movement type cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }
    }
}