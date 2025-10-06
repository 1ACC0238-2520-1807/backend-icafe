package com.synccafe.icafe.inventory.domain.model.aggregates;

import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.ReorderPoint;
import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class SupplyItem extends AuditableAbstractAggregateRoot<SupplyItem> {
    
    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitMeasureType unidadMedida;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "valor", column = @Column(name = "cantidad_actual_valor")),
        @AttributeOverride(name = "unidadMedida", column = @Column(name = "cantidad_actual_unidad"))
    })
    private Quantity cantidadActual;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "cantidadMinima", column = @Column(name = "punto_reorden"))
    })
    private ReorderPoint puntoDeReorden;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_management_id")
    private SupplyManagement supplyManagement;

    protected SupplyItem() {}

    public SupplyItem(String nombre, UnitMeasureType unidadMedida, Quantity cantidadInicial, ReorderPoint puntoDeReorden) {
        this();
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadInicial;
        this.puntoDeReorden = puntoDeReorden;
        
        validateUnitMeasureConsistency();
    }

    public void updateQuantity(Quantity newQuantity) {
        if (!newQuantity.unidadMedida().equals(this.unidadMedida)) {
            throw new IllegalArgumentException("La unidad de medida debe coincidir con la del insumo");
        }
        this.cantidadActual = newQuantity;
    }

    public void addQuantity(Quantity quantityToAdd) {
        if (!quantityToAdd.unidadMedida().equals(this.unidadMedida)) {
            throw new IllegalArgumentException("La unidad de medida debe coincidir con la del insumo");
        }
        this.cantidadActual = this.cantidadActual.add(quantityToAdd);
    }

    public void subtractQuantity(Quantity quantityToSubtract) {
        if (!quantityToSubtract.unidadMedida().equals(this.unidadMedida)) {
            throw new IllegalArgumentException("La unidad de medida debe coincidir con la del insumo");
        }
        this.cantidadActual = this.cantidadActual.subtract(quantityToSubtract);
    }

    public boolean isLowStock() {
        return puntoDeReorden.isReorderNeeded(cantidadActual);
    }

    public void updateReorderPoint(ReorderPoint newReorderPoint) {
        this.puntoDeReorden = newReorderPoint;
    }

    private void validateUnitMeasureConsistency() {
        if (!cantidadActual.unidadMedida().equals(unidadMedida)) {
            throw new IllegalArgumentException("La unidad de medida de la cantidad debe coincidir con la unidad del insumo");
        }
    }
}