package com.synccafe.icafe.inventory.domain.model.commands;

public record RemoveSupplyItemCommand(Long supplyItemId) {
    public RemoveSupplyItemCommand {
        if (supplyItemId == null) {
            throw new IllegalArgumentException("El ID del insumo no puede ser nulo");
        }
    }
}