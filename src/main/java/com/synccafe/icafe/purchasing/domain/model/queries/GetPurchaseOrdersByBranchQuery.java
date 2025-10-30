package com.synccafe.icafe.purchasing.domain.model.queries;

public record GetPurchaseOrdersByBranchQuery(Long branchId) {
    public GetPurchaseOrdersByBranchQuery {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("Branch ID must be greater than zero");
        }
    }
}