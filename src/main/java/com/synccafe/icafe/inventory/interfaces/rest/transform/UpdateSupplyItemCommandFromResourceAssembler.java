package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.inventory.interfaces.rest.resources.UpdateSupplyItemResource;

public class UpdateSupplyItemCommandFromResourceAssembler {

    public static UpdateSupplyItemCommand toCommandFromResource(Long supplyItemId, UpdateSupplyItemResource resource) {
        return new UpdateSupplyItemCommand(
            supplyItemId,
            resource.nombre(),
            resource.unidadMedida(),
            resource.puntoDeReorden()
        );
    }
}