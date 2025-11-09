package com.synccafe.icafe.inventory.domain.model.queries;

public record GetCurrentStockQuery(Long branchId, Long supplyItemId) {
    public GetCurrentStockQuery {
        if (branchId == null) {
            throw new IllegalArgumentException("Branch ID cannot be null");
        }
        if (supplyItemId == null) {
            throw new IllegalArgumentException("SupplyItem ID cannot be null");
        }
    }
}