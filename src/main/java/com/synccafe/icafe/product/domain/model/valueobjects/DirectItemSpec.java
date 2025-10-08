package com.synccafe.icafe.product.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record DirectItemSpec(
        Long itemId,
        Double portionFactor,
        @Enumerated(EnumType.STRING)
        UnitType unit
) {
    public DirectItemSpec {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("El ID del item debe ser mayor a cero");
        }
        if (portionFactor == null || portionFactor <= 0) {
            throw new IllegalArgumentException("El factor de porción debe ser mayor a cero");
        }
        if (unit == null) {
            throw new IllegalArgumentException("La unidad no puede ser nula");
        }
    }
}