package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.CreateSupplyItemCommand;
import com.synccafe.icafe.product.interfaces.rest.resources.CreateSupplyItemResource;

public class CreateSupplyItemCommandFromResourceAssembler {
    public static CreateSupplyItemCommand toCommandFromResource(CreateSupplyItemResource resource) {
        return new CreateSupplyItemCommand(
                resource.providerId(),
                resource.branchId(),
                resource.name(),
                resource.unit(),
                resource.unitPrice(),
                resource.stock(),
                resource.expiredDate()
        );
    }
}
