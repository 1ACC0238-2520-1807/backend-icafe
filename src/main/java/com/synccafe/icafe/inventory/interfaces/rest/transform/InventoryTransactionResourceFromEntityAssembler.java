package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;
import com.synccafe.icafe.inventory.interfaces.rest.resources.InventoryTransactionResource;

public class InventoryTransactionResourceFromEntityAssembler {

    public static InventoryTransactionResource toResourceFromEntity(InventoryTransaction entity) {
        return new InventoryTransactionResource(
            entity.getId(),
            entity.getTipoMovimiento(),
            entity.getCantidad().valor(),
            entity.getCantidad().unidadMedida(),
            entity.getFecha(),
            entity.getReferencia(),
            entity.getSupplyManagement() != null ? entity.getSupplyManagement().getId() : null
        );
    }
}