package com.synccafe.icafe.contacs.interfaces.rest.transform;

import com.synccafe.icafe.contacs.domain.model.commands.UpdateEmployeeContactCommand;
import com.synccafe.icafe.contacs.interfaces.rest.resources.UpdateEmployeeContactResource;

public class UpdateEmployeeContactCommandFromResourceAssembler {
    public static UpdateEmployeeContactCommand toCommandFromResource(UpdateEmployeeContactResource resource) {
        return new UpdateEmployeeContactCommand(
                resource.name(),
                resource.email(),
                resource.phoneNumber(),
                resource.role(),
                resource.salary(),
                resource.branchId()
        );
    }
}
