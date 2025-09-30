package com.synccafe.icafe.contacs.domain.model.entities;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.contacs.domain.model.valueobjects.Role_Employee;
import com.synccafe.icafe.iam.domain.model.entities.Role;
import com.synccafe.icafe.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class EmployeeContact extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "roleemployee_id"))
    private Set<Role_Employee> roles;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private ContactPortfolio portfolio;

    @Embedded
    private BranchId branchId;

    protected EmployeeContact() {
        this.roles = new HashSet<>();
    }

    public EmployeeContact(String name, String email, String phoneNumber, Long branchId) {
        this();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.branchId = new BranchId(branchId);
        this.roles = new HashSet<>();
    }

    public EmployeeContact(String name, String email, String phoneNumber, Long branchId, List<Role_Employee> roles) {
        this(name, email, phoneNumber, branchId);
        addRoles(roles);
    }

    public EmployeeContact addRole(Role_Employee role) {
        this.roles.add(role);
        return this;
    }

    public EmployeeContact addRoles(List<Role_Employee> roles) {
        this.roles.addAll(roles);
        return this;
    }
}
