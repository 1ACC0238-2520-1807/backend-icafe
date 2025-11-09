package com.synccafe.icafe.sales.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record BranchId(Long branchId) {
    public BranchId {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("Branch ID cannot be null or negative");
        }
    }
}