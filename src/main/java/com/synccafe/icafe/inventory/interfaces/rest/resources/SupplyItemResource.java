package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record SupplyItemResource(
    Long id,
    String nombre,
    UnitMeasureType unidadMedida,
    Double cantidadActual,
    Double puntoDeReorden,
    Boolean requiereReabastecimiento,
    Long supplyManagementId
) {
}