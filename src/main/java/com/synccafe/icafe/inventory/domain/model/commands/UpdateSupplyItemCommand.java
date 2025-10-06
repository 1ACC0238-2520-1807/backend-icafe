package com.synccafe.icafe.inventory.domain.model.commands;

import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record UpdateSupplyItemCommand(
        Long supplyItemId,
        String nombre,
        UnitMeasureType unidadMedida,
        Double puntoDeReorden
) {
    public UpdateSupplyItemCommand {
        if (supplyItemId == null) {
            throw new IllegalArgumentException("El ID del insumo no puede ser nulo");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del insumo no puede estar vacío");
        }
        if (unidadMedida == null) {
            throw new IllegalArgumentException("La unidad de medida no puede ser nula");
        }
        if (puntoDeReorden == null || puntoDeReorden < 0) {
            throw new IllegalArgumentException("El punto de reorden debe ser mayor o igual a cero");
        }
    }
}