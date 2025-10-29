package com.synccafe.icafe.inventory.domain.model.queries;

public record GetCurrentStockQuery(Long productId) {
    public GetCurrentStockQuery {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}