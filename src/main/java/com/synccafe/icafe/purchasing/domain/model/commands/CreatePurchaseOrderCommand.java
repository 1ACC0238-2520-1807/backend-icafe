package com.synccafe.icafe.purchasing.domain.model.commands;

import java.time.LocalDate;

public record CreatePurchaseOrderCommand(
    Long branchId,
    String supplierName,
    String supplierEmail,
    String supplierPhone,
    Long supplyItemId,
    Double unitPrice,
    Double quantity,
    LocalDate purchaseDate,
    LocalDate expirationDate,
    String notes
) {
    public CreatePurchaseOrderCommand {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
        if (supplierName == null || supplierName.isBlank()) {
            throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío");
        }
        if (supplyItemId == null || supplyItemId <= 0) {
            throw new IllegalArgumentException("El ID del insumo debe ser mayor a cero");
        }
        if (unitPrice == null || unitPrice <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a cero");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (purchaseDate == null) {
            throw new IllegalArgumentException("La fecha de compra no puede ser nula");
        }
        if (expirationDate != null && expirationDate.isBefore(purchaseDate)) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la fecha de compra");
        }
    }
}