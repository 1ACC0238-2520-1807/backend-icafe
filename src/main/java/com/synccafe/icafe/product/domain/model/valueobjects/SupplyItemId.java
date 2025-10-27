package com.synccafe.icafe.product.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record SupplyItemId(
    Long supplyItemId
) {
    public SupplyItemId {
        if (supplyItemId == null || supplyItemId <= 0) {
            throw new IllegalArgumentException("El ID del insumo debe ser mayor a cero");
        }
    }
}
