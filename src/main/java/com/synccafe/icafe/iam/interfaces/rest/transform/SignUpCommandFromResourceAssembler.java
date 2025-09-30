package com.synccafe.icafe.iam.interfaces.rest.transform;

import com.synccafe.icafe.iam.domain.model.commands.SignUpCommand;
import com.synccafe.icafe.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.email(), resource.password(), resource.roles());
    }

}
