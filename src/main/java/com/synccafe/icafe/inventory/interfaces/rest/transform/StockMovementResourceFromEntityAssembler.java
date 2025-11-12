package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.inventory.interfaces.rest.resources.StockMovementResource;

public class StockMovementResourceFromEntityAssembler {
    public static StockMovementResource toResourceFromEntity(StockMovement entity) {
        return new StockMovementResource(
            entity.getId(),
            entity.getSupplyItemId(),
            entity.getBranchId().branchId(),
            entity.getType(),
            entity.getQuantity(),
            entity.getOrigin(),
            entity.getMovementDate()
        );
    }
}