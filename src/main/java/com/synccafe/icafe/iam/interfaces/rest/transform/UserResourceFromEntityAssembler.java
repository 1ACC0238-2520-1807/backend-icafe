package com.synccafe.icafe.iam.interfaces.rest.transform;

import com.synccafe.icafe.iam.domain.model.aggregates.User;
import com.synccafe.icafe.iam.domain.model.entities.Role;
import com.synccafe.icafe.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getEmail(), roles);
    }
}
