package com.synccafe.icafe.sales.domain.model.aggregates;

import com.synccafe.icafe.sales.domain.model.commands.CreateSaleCommand;
import com.synccafe.icafe.sales.domain.model.events.SaleCreatedEvent;
import com.synccafe.icafe.sales.domain.model.valueobjects.*;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
public class Sale extends AuditableAbstractAggregateRoot<Sale> {

    @Embedded
    @AttributeOverride(name = "customerId", column = @Column(name = "customer_id"))
    private CustomerId customerId;

    @Embedded
    @AttributeOverride(name = "branchId", column = @Column(name = "branch_id"))
    private BranchId branchId;

    @ElementCollection
    @CollectionTable(name = "sale_items", joinColumns = @JoinColumn(name = "sale_id"))
    private List<SaleItem> items = new ArrayList<>();

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_amount"))
    private Money totalAmount;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SaleStatus status;

    @Column(name = "notes")
    private String notes;

    protected Sale() {}

    public Sale(CreateSaleCommand command) {
        this();
        this.customerId = new CustomerId(command.customerId());
        this.branchId = new BranchId(command.branchId());
        this.saleDate = LocalDateTime.now();
        this.status = SaleStatus.PENDING;
        this.notes = command.notes();
        
        // Add items and calculate total
        Money total = new Money(0.0);
        for (var itemCommand : command.items()) {
            SaleItem item = new SaleItem(
                itemCommand.productId(),
                itemCommand.quantity(),
                new Money(itemCommand.unitPrice())
            );
            this.items.add(item);
            total = total.add(item.getSubtotal());
        }
        this.totalAmount = total;
        
        // Register domain event
        this.registerEvent(new SaleCreatedEvent(this));
    }

    public void completeSale() {
        if (this.status != SaleStatus.PENDING) {
            throw new IllegalStateException("Only pending sales can be completed");
        }
        this.status = SaleStatus.COMPLETED;
    }

    public void cancelSale() {
        if (this.status == SaleStatus.COMPLETED) {
            throw new IllegalStateException("Completed sales cannot be cancelled");
        }
        this.status = SaleStatus.CANCELLED;
    }
}