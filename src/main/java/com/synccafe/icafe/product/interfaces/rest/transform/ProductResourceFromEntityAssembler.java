package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.interfaces.rest.resources.ProductIngredientResource;
import com.synccafe.icafe.product.interfaces.rest.resources.ProductResource;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResourceFromEntityAssembler {

    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId(),
                entity.getBranchId().branchId(),
                entity.getName(),
                entity.getCostPrice().amount(),
                entity.getSalePrice().amount(),
                entity.getProfitMargin(),
                entity.getStatus(),
                entity.getIngredients().stream()
                        .map(i -> new ProductIngredientResource(i.getSupplyItemId().supplyItemId(), i.getQuantity()))
                        .collect(Collectors.toSet())
        );
    }
}