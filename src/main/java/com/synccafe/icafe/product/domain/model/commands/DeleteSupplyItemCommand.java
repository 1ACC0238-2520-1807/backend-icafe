package com.synccafe.icafe.product.domain.model.commands;

public record DeleteSupplyItemCommand(Long SupplyItemId) {
    public DeleteSupplyItemCommand {
        if (SupplyItemId == null || SupplyItemId <= 0) {
            throw new IllegalArgumentException("El ID del insumo debe ser mayor a cero");
        }
    }
}
