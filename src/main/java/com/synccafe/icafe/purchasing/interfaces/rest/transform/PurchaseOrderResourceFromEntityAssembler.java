package com.synccafe.icafe.purchasing.interfaces.rest.transform;

import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.interfaces.rest.resources.PurchaseOrderResource;

public class PurchaseOrderResourceFromEntityAssembler {
    
    public static PurchaseOrderResource toResourceFromEntity(PurchaseOrder entity) {
        return new PurchaseOrderResource(
            entity.getId(),
            entity.getBranchId().branchId(),
            entity.getSupplierContact().name(),
            entity.getSupplierContact().email(),
            entity.getSupplierContact().phone(),
            entity.getSupplyItemId(),
            entity.getUnitPrice().amount(),
            entity.getQuantity(),
            entity.getTotalAmount().amount(),
            entity.getPurchaseDate(),
            entity.getExpirationDate(),
            entity.getStatus(),
            entity.getNotes()
        );
    }
}