package com.synccafe.icafe.product.interfaces.rest.transform;


import com.synccafe.icafe.product.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateSupplyItemResource;

public class UpdateSupplyItemCommandFromResourceAssembler {
    public static UpdateSupplyItemCommand toCommandFromResource(UpdateSupplyItemResource resource) {
        return new UpdateSupplyItemCommand(
                resource.name(),
                resource.unitPrice(),
                resource.stock(),
                resource.expiredDate()
        );
    }
}
