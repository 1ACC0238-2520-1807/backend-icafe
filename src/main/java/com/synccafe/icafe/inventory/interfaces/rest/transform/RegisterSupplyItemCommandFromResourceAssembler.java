package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterSupplyItemCommand;
import com.synccafe.icafe.inventory.interfaces.rest.resources.CreateSupplyItemResource;

public class RegisterSupplyItemCommandFromResourceAssembler {

    public static RegisterSupplyItemCommand toCommandFromResource(CreateSupplyItemResource resource) {
        return new RegisterSupplyItemCommand(
            resource.nombre(),
            resource.unidadMedida(),
            resource.cantidadInicial(),
            resource.puntoDeReorden(),
            resource.supplyManagementId()
        );
    }
}