package com.synccafe.icafe.product.domain.model.queries;

public record GetAllProductsByBranchIdQuery(Long branchId) {
    public GetAllProductsByBranchIdQuery {
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a cero");
        }
    }
}
