package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterStockMovementCommand;
import com.synccafe.icafe.inventory.interfaces.rest.resources.RegisterStockMovementResource;

public class RegisterStockMovementCommandFromResourceAssembler {
    
    public static RegisterStockMovementCommand toCommandFromResource(RegisterStockMovementResource resource) {
        return new RegisterStockMovementCommand(
            resource.productId(),
            resource.type(),
            resource.quantity(),
            resource.origin()
        );
    }
}