package com.synccafe.icafe.inventory.domain.model.entities;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyManagement;
import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class InventoryTransaction extends AuditableAbstractAggregateRoot<InventoryTransaction> {
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType tipoMovimiento;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "valor", column = @Column(name = "cantidad_valor")),
        @AttributeOverride(name = "unidadMedida", column = @Column(name = "cantidad_unidad"))
    })
    private Quantity cantidad;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 500)
    private String referencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_management_id")
    private SupplyManagement supplyManagement;

    protected InventoryTransaction() {}

    public InventoryTransaction(TransactionType tipoMovimiento, Quantity cantidad, String referencia) {
        this();
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.fecha = LocalDateTime.now();
    }

    public void setSupplyManagement(SupplyManagement supplyManagement) {
        this.supplyManagement = supplyManagement;
    }
}