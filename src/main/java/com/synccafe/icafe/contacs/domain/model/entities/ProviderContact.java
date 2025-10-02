package com.synccafe.icafe.contacs.domain.model.entities;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class ProviderContact extends AuditableAbstractAggregateRoot<ProviderContact> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    private String nameCompany;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private String ruc;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private ContactPortfolio portfolio;

    public ProviderContact() {

    }

    public ProviderContact(String nameCompany, String email, String phoneNumber, String ruc) {
        this.nameCompany = nameCompany;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ruc = ruc;
    }

    public ProviderContact(CreateProviderContactCommand command) {
        this.nameCompany = command.nameCompany();
        this.email = command.email();
        this.phoneNumber = command.phoneNumber();
        this.ruc = command.ruc();
    }
    // Getters and Setters
    public void setPortfolio(ContactPortfolio portfolio) {
        this.portfolio = portfolio;
    }

}
