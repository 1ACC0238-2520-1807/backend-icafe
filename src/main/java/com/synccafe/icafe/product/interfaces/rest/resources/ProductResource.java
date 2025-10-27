package com.synccafe.icafe.product.interfaces.rest.resources;

import com.synccafe.icafe.product.domain.model.valueobjects.ProductStatus;

import java.util.Set;

public record ProductResource(
        Long id,
        Long branchId,
        String name,
        double costPrice,
        double salePrice,
        double profitMargin,
        ProductStatus status,
        Set<ProductIngredientResource> ingredients
) {
}