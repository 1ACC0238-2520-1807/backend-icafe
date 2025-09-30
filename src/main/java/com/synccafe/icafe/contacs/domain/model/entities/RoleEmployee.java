package com.synccafe.icafe.contacs.domain.model.entities;

import com.synccafe.icafe.contacs.domain.model.valueobjects.RolesEmployee;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
public class RoleEmployee {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private RolesEmployee name;

    public RoleEmployee() {}
    public RoleEmployee(RolesEmployee name) {
        this.name = name;
    }

    public String getStringName() {
        return name.name();
    }

    public static RoleEmployee getDefaultRole() {
        return new RoleEmployee(RolesEmployee.BARISTA);
    }

    public static RoleEmployee toRoleFromName(String name) {
        return new RoleEmployee(RolesEmployee.valueOf(name));
    }

    public static List<RoleEmployee> validateRoleSet(List<RoleEmployee> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return roles;
    }
}
