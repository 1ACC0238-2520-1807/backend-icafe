package com.synccafe.icafe.product.interfaces.rest.transform;


import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import com.synccafe.icafe.product.interfaces.rest.resources.SupplyItemResource;

public class SupplyItemResourceFromEntityAssembler {
    public static SupplyItemResource toResourceFromEntity(SupplyItem entity) {
        return new SupplyItemResource(
                entity.getId(),
                entity.getProviderId().providerId(),
                entity.getBranchId().branchId(),
                entity.getName(),
                entity.getUnit(),
                entity.getUnitPrice(),
                entity.getStock(),
                entity.getBuyDate(),
                entity.getExpiredDate()
        );
    }
}
