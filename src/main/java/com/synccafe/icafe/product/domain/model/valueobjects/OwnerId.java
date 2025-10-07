package com.synccafe.icafe.product.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OwnerId(Long ownerId) {
    public OwnerId {
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException("El ID del propietario debe ser mayor a cero");
        }
    }
}