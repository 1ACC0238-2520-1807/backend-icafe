package com.synccafe.icafe.shared.domain.model.entities;


import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.synccafe.icafe.shared.domain.model.commands.CreateBranchCommand;
import com.synccafe.icafe.shared.domain.model.commands.UpdateBranchCommand;
import com.synccafe.icafe.shared.domain.model.valueobjects.OwnerId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
public class Branch extends AuditableAbstractAggregateRoot<Branch> {


    @Embedded
    private OwnerId ownerId;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;

    public Branch( Long ownerId){
        super();
        this.ownerId = new OwnerId(ownerId);
    }
    public Branch(OwnerId ownerId) {
        super();
        this.ownerId = ownerId;
    }

    public Branch(CreateBranchCommand command) {
        this.ownerId = new OwnerId(command.ownerId());
        this.name = command.name();
        this.address = command.address();
    }

    public void updateBranch(UpdateBranchCommand command) {
        this.name = command.name();
        this.address = command.address();
    }
}
