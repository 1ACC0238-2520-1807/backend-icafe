package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record UpdateSupplyItemResource(
    String nombre,
    UnitMeasureType unidadMedida,
    Double puntoDeReorden
) {
}