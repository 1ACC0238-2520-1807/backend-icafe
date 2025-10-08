package com.synccafe.icafe.product.domain.model.queries;

public record GetAllProductsQuery(
        Long ownerId,
        Long branchId
) {
    public GetAllProductsQuery {
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException("El ID del propietario debe ser mayor a cero");
        }
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
    }
}