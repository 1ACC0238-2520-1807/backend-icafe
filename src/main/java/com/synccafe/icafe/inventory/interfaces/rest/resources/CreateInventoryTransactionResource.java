package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

public record CreateInventoryTransactionResource(
    Long supplyItemId,
    TransactionType tipoMovimiento,
    Double cantidad,
    UnitMeasureType unidadMedida,
    String referencia
) {
}