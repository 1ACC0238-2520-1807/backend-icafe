package com.synccafe.icafe.contacs.domain.model.aggregates;

import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.model.valueobjects.UserId;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class
ContactPortfolio extends AuditableAbstractAggregateRoot<ContactPortfolio> {
    @Embedded
    private UserId userId;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeContact> employees = new HashSet<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProviderContact> providers = new HashSet<>();

    protected ContactPortfolio() {}

    public ContactPortfolio(Long userId) {
        this();
        this.userId = new UserId(userId);
    }

    public ContactPortfolio(UserId userId) {
        this();
        this.userId = userId;
    }
    public Set<ProviderContact> getProviders() {
        return providers;
    }
    public Set<EmployeeContact> getEmployees() {
        return employees;
    }

    public void addEmployee(EmployeeContact employee) {
        employees.add(employee);
    }

    public void addProvider(ProviderContact provider) {
        providers.add(provider);
    }

    public Long getUserId() { return userId.userId(); }
}
