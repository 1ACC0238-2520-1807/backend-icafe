package com.synccafe.icafe.iam.interfaces.rest.transform;

import com.synccafe.icafe.iam.domain.model.commands.SignInCommand;
import com.synccafe.icafe.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.email(), signInResource.password());
    }
}
