package com.synccafe.icafe.purchasing.domain.model.queries;

public record GetPurchaseOrderByIdQuery(Long purchaseOrderId, Long branchId) {
    public GetPurchaseOrderByIdQuery {
        if (purchaseOrderId == null || purchaseOrderId <= 0) {
            throw new IllegalArgumentException("Purchase Order ID must be greater than zero");
        }
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("Branch ID must be greater than zero");
        }
    }
}