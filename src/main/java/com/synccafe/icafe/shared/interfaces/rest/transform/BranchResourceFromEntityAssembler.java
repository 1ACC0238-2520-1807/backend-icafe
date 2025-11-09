package com.synccafe.icafe.shared.interfaces.rest.transform;

import com.synccafe.icafe.shared.domain.model.entities.Branch;
import com.synccafe.icafe.shared.interfaces.rest.resources.BranchResource;

public class BranchResourceFromEntityAssembler {
    public static BranchResource toResourceFromEntity(Branch entity){
        return new BranchResource(
                entity.getId(),
                entity.getName(),
                entity.getAddress()
        );
    }
}
