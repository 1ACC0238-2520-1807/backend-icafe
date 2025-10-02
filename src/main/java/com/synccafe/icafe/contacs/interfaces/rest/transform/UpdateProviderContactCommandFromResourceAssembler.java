package com.synccafe.icafe.contacs.interfaces.rest.transform;

import com.synccafe.icafe.contacs.domain.model.commands.UpdateProviderContactCommand;
import com.synccafe.icafe.contacs.interfaces.rest.resources.UpdateProviderContactResource;

public class UpdateProviderContactCommandFromResourceAssembler {
    public static UpdateProviderContactCommand toCommandFromResource(UpdateProviderContactResource resource) {
        return new UpdateProviderContactCommand(
                resource.nameCompany(),
                resource.email(),
                resource.phoneNumber(),
                resource.ruc()
        );
    }
}
