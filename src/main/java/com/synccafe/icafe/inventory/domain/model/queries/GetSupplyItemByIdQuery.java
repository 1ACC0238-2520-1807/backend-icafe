package com.synccafe.icafe.inventory.domain.model.queries;

public record GetSupplyItemByIdQuery(Long supplyItemId) {
    public GetSupplyItemByIdQuery {
        if (supplyItemId == null) {
            throw new IllegalArgumentException("El ID del insumo no puede ser nulo");
        }
    }
}