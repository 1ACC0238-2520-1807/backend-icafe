package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.interfaces.rest.resources.SupplyItemResource;

public class SupplyItemResourceFromEntityAssembler {

    public static SupplyItemResource toResourceFromEntity(SupplyItem entity) {
        return new SupplyItemResource(
            entity.getId(),
            entity.getNombre(),
            entity.getUnidadMedida(),
            entity.getCantidadActual().valor(),
            entity.getPuntoDeReorden().cantidadMinima(),
            entity.isLowStock(),
            entity.getSupplyManagement() != null ? entity.getSupplyManagement().getId() : null
        );
    }
}