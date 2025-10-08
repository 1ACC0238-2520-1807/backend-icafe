package com.synccafe.icafe.inventory.domain.model.commands;

import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record RegisterSupplyItemCommand(
        String nombre,
        UnitMeasureType unidadMedida,
        Double cantidadInicial,
        Double puntoDeReorden,
        Long supplyManagementId
) {
    public RegisterSupplyItemCommand {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del insumo no puede estar vacío");
        }
        if (unidadMedida == null) {
            throw new IllegalArgumentException("La unidad de medida no puede ser nula");
        }
        if (cantidadInicial == null || cantidadInicial < 0) {
            throw new IllegalArgumentException("La cantidad inicial debe ser mayor o igual a cero");
        }
        if (puntoDeReorden == null || puntoDeReorden < 0) {
            throw new IllegalArgumentException("El punto de reorden debe ser mayor o igual a cero");
        }
        if (supplyManagementId == null) {
            throw new IllegalArgumentException("El ID de gestión de suministros no puede ser nulo");
        }
    }
}