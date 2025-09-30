package com.synccafe.icafe.iam.domain.service;

import com.synccafe.icafe.iam.domain.model.entities.Role;
import com.synccafe.icafe.iam.domain.model.queries.GetAllRolesQuery;
import com.synccafe.icafe.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
