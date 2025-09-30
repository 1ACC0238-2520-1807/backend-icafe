package com.synccafe.icafe.iam.application.internal.queryservices;

import com.synccafe.icafe.iam.domain.model.entities.Role;
import com.synccafe.icafe.iam.domain.model.queries.GetAllRolesQuery;
import com.synccafe.icafe.iam.domain.model.queries.GetRoleByNameQuery;
import com.synccafe.icafe.iam.domain.service.RoleQueryService;
import com.synccafe.icafe.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {
    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> handle(GetRoleByNameQuery query) {
        return roleRepository.findByName(query.roleName());
    }
}
