package com.synccafe.icafe.contacs.interfaces.rest.transform;

import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.interfaces.rest.resources.CreateEmployeeContactResource;

public class CreateEmployeeContactCommandFromResourceAssembler {
    public static CreateEmployeeContactCommand toCommandFromResource(CreateEmployeeContactResource resource){
        return new CreateEmployeeContactCommand(
                resource.name(),
                resource.email(),
                resource.phoneNumber(),
                resource.role(),
                resource.salary(),
                resource.branchId()
        );
    }
}
