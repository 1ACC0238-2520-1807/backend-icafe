package com.synccafe.icafe.purchasing.interfaces.rest.transform;

import com.synccafe.icafe.purchasing.domain.model.commands.CreatePurchaseOrderCommand;
import com.synccafe.icafe.purchasing.interfaces.rest.resources.CreatePurchaseOrderResource;

public class CreatePurchaseOrderCommandFromResourceAssembler {
    
    public static CreatePurchaseOrderCommand toCommandFromResource(CreatePurchaseOrderResource resource) {
        return new CreatePurchaseOrderCommand(
            resource.branchId(),
            resource.supplierName(),
            resource.supplierEmail(),
            resource.supplierPhone(),
            resource.supplyItemId(),
            resource.unitPrice(),
            resource.quantity(),
            resource.purchaseDate(),
            resource.expirationDate(),
            resource.notes()
        );
    }
}