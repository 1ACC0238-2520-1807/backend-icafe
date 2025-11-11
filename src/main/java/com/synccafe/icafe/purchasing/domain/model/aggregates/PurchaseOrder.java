package com.synccafe.icafe.purchasing.domain.model.aggregates;

import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.model.valueobjects.Money;
import com.synccafe.icafe.purchasing.domain.model.commands.CreatePurchaseOrderCommand;
import com.synccafe.icafe.purchasing.domain.model.events.PurchaseOrderCreatedEvent;
import com.synccafe.icafe.purchasing.domain.model.valueobjects.PurchaseOrderStatus;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "purchase_orders")
@Getter
public class PurchaseOrder extends AuditableAbstractAggregateRoot<PurchaseOrder> {
    
    @Embedded
    @AttributeOverride(name = "branchId", column = @Column(name = "branch_id", nullable = false))
    private BranchId branchId;
    
    @Column(name = "provider_id", nullable = false)
    private Long providerId;
    
    @Column(name = "supply_item_id", nullable = false)
    private Long supplyItemId;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "unit_price", nullable = false))
    })
    private Money unitPrice;
    
    @Column(nullable = false)
    private Double quantity;
    
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;
    
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseOrderStatus status;
    
    @Column(length = 500)
    private String notes;

    protected PurchaseOrder() {}

    public PurchaseOrder(CreatePurchaseOrderCommand command) {
        this.branchId = new BranchId(command.branchId());
        this.providerId = command.providerId();
        this.supplyItemId = command.supplyItemId();
        this.unitPrice = new Money(command.unitPrice());
        this.quantity = command.quantity();
        this.purchaseDate = command.purchaseDate();
        this.expirationDate = command.expirationDate();
        this.status = PurchaseOrderStatus.PENDING;
        this.notes = command.notes();
    }

    public void registerCreatedEvent() {
        this.registerEvent(new PurchaseOrderCreatedEvent(
            this.getId(),
            this.branchId.branchId(),
            this.supplyItemId,
            this.quantity,
            this.purchaseDate,
            java.time.Instant.now()
        ));
    }

    public void confirm() {
        if (this.status != PurchaseOrderStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden confirmar órdenes pendientes");
        }
        this.status = PurchaseOrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == PurchaseOrderStatus.COMPLETED) {
            throw new IllegalStateException("No se pueden cancelar órdenes completadas");
        }
        this.status = PurchaseOrderStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != PurchaseOrderStatus.CONFIRMED) {
            throw new IllegalStateException("Solo se pueden completar órdenes confirmadas");
        }
        this.status = PurchaseOrderStatus.COMPLETED;
    }

    public Money getTotalAmount() {
        return new Money(unitPrice.amount() * quantity);
    }
}