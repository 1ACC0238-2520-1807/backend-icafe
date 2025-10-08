package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterInventoryTransactionCommand;
import com.synccafe.icafe.inventory.interfaces.rest.resources.CreateInventoryTransactionResource;

public class RegisterInventoryTransactionCommandFromResourceAssembler {

    public static RegisterInventoryTransactionCommand toCommandFromResource(CreateInventoryTransactionResource resource) {
        return new RegisterInventoryTransactionCommand(
            resource.supplyItemId(),
            resource.tipoMovimiento(),
            resource.cantidad(),
            resource.unidadMedida(),
            resource.referencia()
        );
    }
}