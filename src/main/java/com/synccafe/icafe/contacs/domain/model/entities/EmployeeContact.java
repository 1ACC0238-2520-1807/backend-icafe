package com.synccafe.icafe.contacs.domain.model.entities;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class EmployeeContact extends AuditableAbstractAggregateRoot<EmployeeContact> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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
    @Setter
    private String role;

    @Getter
    @Setter
    private String salary;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private ContactPortfolio portfolio;

    @Embedded
    private BranchId branchId;

    protected EmployeeContact() {
    }

    public EmployeeContact(String name, String email, String phoneNumber,String role,String salary, Long branchId) {
        this();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.salary = salary;
        this.branchId = new BranchId(branchId);
    }

    public EmployeeContact(CreateEmployeeContactCommand command) {
        this.name = command.name();
        this.email = command.email();
        this.phoneNumber = command.phoneNumber();
        this.role = command.role();
        this.salary = command.salary();
        this.branchId = new BranchId(command.branchId());
    }


    public Long getBranchId() {
        return branchId.branchId();
    }
    //setBranchId
    public void setBranchId(Long branchId) {
        this.branchId = new BranchId(branchId);
    }


    // Getters and Setters
    public void setPortfolio(ContactPortfolio portfolio) {
        this.portfolio = portfolio;
    }
}