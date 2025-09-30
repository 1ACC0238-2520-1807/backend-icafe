package com.synccafe.icafe.contacs.interfaces.rest.transform;

import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.interfaces.rest.resources.EmployeeContactResource;

public class EmployeeContactResourceFromEntityAssembler {
    public static EmployeeContactResource toResourceFromEntity(EmployeeContact entity){
        var roles = entity.getRoles().stream().map(role -> role.getStringName()).toList();
        return new EmployeeContactResource(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                roles,
                entity.getBranchId());
    }
}
