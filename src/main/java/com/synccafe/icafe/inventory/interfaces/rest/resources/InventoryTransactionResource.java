package com.synccafe.icafe.inventory.interfaces.rest.resources;

import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import com.synccafe.icafe.inventory.domain.model.valueobjects.UnitMeasureType;

import java.time.LocalDateTime;

public record InventoryTransactionResource(
    Long id,
    TransactionType tipoMovimiento,
    Double cantidad,
    UnitMeasureType unidadMedida,
    LocalDateTime fechaTransaccion,
    String referencia,
    Long supplyManagementId
) {
}