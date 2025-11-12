package com.synccafe.icafe.inventory.domain.model.queries;

public record GetStockMovementsByBranchQuery(Long branchId) {
    public GetStockMovementsByBranchQuery {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("Branch ID must be greater than zero");
        }
    }
}