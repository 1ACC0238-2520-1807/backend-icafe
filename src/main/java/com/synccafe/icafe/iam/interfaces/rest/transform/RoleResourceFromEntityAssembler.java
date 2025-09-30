package com.synccafe.icafe.iam.interfaces.rest.transform;

import com.synccafe.icafe.iam.domain.model.entities.Role;
import com.synccafe.icafe.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
