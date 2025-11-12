package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;

import java.time.LocalDateTime;

public record StockMovementResource(
    Long id,
    Long supplyItemId,
    Long branchId,
    StockMovement.MovementType type,
    Double quantity,
    String origin,
    LocalDateTime movementDate
) {}