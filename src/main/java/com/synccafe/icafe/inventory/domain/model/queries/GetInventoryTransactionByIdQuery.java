package com.synccafe.icafe.inventory.domain.model.queries;

public record GetInventoryTransactionByIdQuery(Long inventoryTransactionId) {
    public GetInventoryTransactionByIdQuery {
        if (inventoryTransactionId == null) {
            throw new IllegalArgumentException("El ID de la transacción de inventario no puede ser nulo");
        }
    }
}