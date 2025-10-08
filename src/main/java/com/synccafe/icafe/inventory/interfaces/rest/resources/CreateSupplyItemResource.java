package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record CreateSupplyItemResource(
    String nombre,
    UnitMeasureType unidadMedida,
    Double cantidadInicial,
    Double puntoDeReorden,
    Long supplyManagementId
) {
}