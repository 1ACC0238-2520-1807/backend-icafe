package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;

public record RegisterStockMovementResource(
    Long productId,
    StockMovement.MovementType type,
    Double quantity,
    String origin
) {}