package com.synccafe.icafe.product.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record RecipeItem(
        Long itemId,
        Double qtyPerPortion,
        @Enumerated(EnumType.STRING)
        UnitType unit
) {
    public RecipeItem {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("El ID del item debe ser mayor a cero");
        }
        if (qtyPerPortion == null || qtyPerPortion <= 0) {
            throw new IllegalArgumentException("La cantidad por porción debe ser mayor a cero");
        }
        if (unit == null) {
            throw new IllegalArgumentException("La unidad no puede ser nula");
        }
    }
}