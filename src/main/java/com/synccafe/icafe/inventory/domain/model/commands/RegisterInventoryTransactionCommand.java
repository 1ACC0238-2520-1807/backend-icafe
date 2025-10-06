package com.synccafe.icafe.inventory.domain.model.commands;

import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record RegisterInventoryTransactionCommand(
        Long supplyItemId,
        TransactionType tipoMovimiento,
        Double cantidad,
        UnitMeasureType unidadMedida,
        String referencia
) {
    public RegisterInventoryTransactionCommand {
        if (supplyItemId == null) {
            throw new IllegalArgumentException("El ID del insumo no puede ser nulo");
        }
        if (tipoMovimiento == null) {
            throw new IllegalArgumentException("El tipo de movimiento no puede ser nulo");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (unidadMedida == null) {
            throw new IllegalArgumentException("La unidad de medida no puede ser nula");
        }
    }
}