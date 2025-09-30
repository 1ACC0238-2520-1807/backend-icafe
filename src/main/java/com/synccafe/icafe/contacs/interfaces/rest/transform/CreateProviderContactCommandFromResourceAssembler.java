package com.synccafe.icafe.contacs.interfaces.rest.transform;

import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.interfaces.rest.resources.CreateProviderContactResource;

public class CreateProviderContactCommandFromResourceAssembler {
    public static CreateProviderContactCommand toCommandFromResource(CreateProviderContactResource resource){
        return new CreateProviderContactCommand(
                resource.nameCompany(),
                resource.email(),
                resource.phoneNumber(),
                resource.ruc()
        );
    }
}
