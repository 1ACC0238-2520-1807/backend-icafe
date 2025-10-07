package com.synccafe.icafe.product.domain.model.queries;

public record GetProductByIdQuery(Long productId) {
    public GetProductByIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a cero");
        }
    }
}