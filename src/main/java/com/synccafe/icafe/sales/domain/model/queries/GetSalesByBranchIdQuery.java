package com.synccafe.icafe.sales.domain.model.queries;

public record GetSalesByBranchIdQuery(Long branchId) {
    public GetSalesByBranchIdQuery {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("Branch ID cannot be null or negative");
        }
    }
}