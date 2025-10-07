package com.synccafe.icafe.product.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record BranchId(Long branchId) {
    public BranchId {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
    }
}