package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.interfaces.rest.resources.ProductResource;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResourceFromEntityAssembler {

    public static ProductResource toResourceFromEntity(Product product) {
        ProductResource.DirectItemSpecResource directItem = null;
        if (product.getDirectItem() != null) {
            directItem = new ProductResource.DirectItemSpecResource(
                    product.getDirectItem().itemId(),
                    product.getDirectItem().quantity()
            );
        }

        List<ProductResource.RecipeItemResource> components = null;
        if (product.getComponents() != null) {
            components = product.getComponents().stream()
                    .map(item -> new ProductResource.RecipeItemResource(
                            item.itemId(),
                            item.quantity()
                    ))
                    .collect(Collectors.toList());
        }

        return new ProductResource(
                product.getId(),
                product.getOwnerId().value(),
                product.getBranchId().value(),
                product.getName(),
                product.getCategory(),
                product.getType(),
                product.getStatus(),
                product.getPortions(),
                product.getSteps(),
                directItem,
                components,
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getVersion()
        );
    }

    public static List<ProductResource> toResourceListFromEntityList(List<Product> products) {
        return products.stream()
                .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}