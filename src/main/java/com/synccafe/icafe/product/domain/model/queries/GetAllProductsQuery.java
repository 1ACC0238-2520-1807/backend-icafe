package com.synccafe.icafe.product.domain.model.queries;

public record GetAllProductsQuery(
        Long branchId
) {
    public GetAllProductsQuery {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
    }
}