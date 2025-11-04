package com.synccafe.icafe.sales.domain.model.queries;

public record GetSaleByIdQuery(Long saleId) {
    public GetSaleByIdQuery {
        if (saleId == null || saleId <= 0) {
            throw new IllegalArgumentException("Sale ID cannot be null or negative");
        }
    }
}