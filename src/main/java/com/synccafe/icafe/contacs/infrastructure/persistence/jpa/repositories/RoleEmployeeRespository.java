package com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.contacs.domain.model.entities.RoleEmployee;
import com.synccafe.icafe.contacs.domain.model.valueobjects.RolesEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleEmployeeRespository extends JpaRepository<RoleEmployee,Long> {
    Optional<RoleEmployee> findByName(RolesEmployee name);
    boolean existsByName(RolesEmployee name);
}
