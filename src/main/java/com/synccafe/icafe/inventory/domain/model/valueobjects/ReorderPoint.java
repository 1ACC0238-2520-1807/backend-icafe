package com.synccafe.icafe.inventory.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ReorderPoint(Double cantidadMinima) {
    public ReorderPoint {
        if (cantidadMinima == null || cantidadMinima < 0) {
            throw new IllegalArgumentException("La cantidad mínima debe ser mayor o igual a cero");
        }
    }

    public boolean isReorderNeeded(Quantity currentQuantity) {
        return currentQuantity.valor() <= cantidadMinima;
    }
}