package com.synccafe.icafe.product.domain.model.entities;

import com.synccafe.icafe.contacs.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.model.commands.CreateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.events.SupplyItemPriceUpdatedEvent;
import com.synccafe.icafe.product.domain.model.valueobjects.ProviderId;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class SupplyItem extends AuditableAbstractAggregateRoot<SupplyItem> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Embedded
    private ProviderId providerId;
    @Getter
    @Embedded
    private BranchId branchId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String unit;
    @Getter
    @Setter
    private double unitPrice;
    @Getter
    @Setter
    private double stock;
    @Getter
    @Setter
    private Date buyDate;
    @Getter
    @Setter
    private Date expiredDate;

    protected SupplyItem() {}

    public SupplyItem(Long providerId, Long branchId, String name, String unit, double unitPrice, double stock) {
        this.providerId = new ProviderId(providerId);
        this.branchId = new BranchId(branchId);
        this.name = name;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public SupplyItem(CreateSupplyItemCommand command) {
        this.providerId = new ProviderId(command.providerId());
        this.branchId = new BranchId(command.branchId());
        this.name = command.name();
        this.unit = command.unit();
        this.unitPrice = command.unitPrice();
        this.stock = command.stock();
        //capturar el tiempo actual cuando se crea
        this.buyDate = new Date();
        this.expiredDate = command.expiredDate();
    }

    public void updateSupplyItem(UpdateSupplyItemCommand command) {
        this.name = command.name();
        this.unit = command.unit();
        this.unitPrice = command.unitPrice();
        this.stock = command.stock();
        this.expiredDate = command.expiredDate();

        // 🔥 Publicar evento
        registerEvent(new SupplyItemPriceUpdatedEvent(this.id, this.unitPrice));
    }
}
