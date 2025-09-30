package com.synccafe.icafe.contacs.domain.model.entities;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.synccafe.icafe.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class EmployeeContact extends AuditableAbstractAggregateRoot<EmployeeContact> {
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
    private Set<RoleEmployee> roles;

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

    public EmployeeContact(CreateEmployeeContactCommand command) {
        this.name = command.name();
        this.email = command.email();
        this.phoneNumber = command.phoneNumber();
        this.roles = new HashSet<>();
        this.branchId = new BranchId(command.branchId());
    }


    public EmployeeContact(String name, String email, String phoneNumber, Long branchId, List<RoleEmployee> roles) {
        this(name, email, phoneNumber, branchId);
        addRoles(roles);
    }

    public EmployeeContact addRole(RoleEmployee role) {
        this.roles.add(role);
        return this;
    }

    public EmployeeContact addRoles(List<RoleEmployee> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public Long getBranchId() {
        return branchId.branchId();
    }
}
