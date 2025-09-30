package com.synccafe.icafe.iam.interfaces.rest.transform;

import com.synccafe.icafe.iam.domain.model.aggregates.User;
import com.synccafe.icafe.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getEmail(), token);
    }
}
