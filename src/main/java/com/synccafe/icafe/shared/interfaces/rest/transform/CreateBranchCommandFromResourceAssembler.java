package com.synccafe.icafe.shared.interfaces.rest.transform;

import com.synccafe.icafe.shared.domain.model.commands.CreateBranchCommand;
import com.synccafe.icafe.shared.interfaces.rest.resources.CreateBranchResource;

public class CreateBranchCommandFromResourceAssembler {
    public static CreateBranchCommand toCommandFromResource(CreateBranchResource resource){
        return new CreateBranchCommand(
                resource.ownerId(),
                resource.name(),
                resource.address()
        );
    }
}
