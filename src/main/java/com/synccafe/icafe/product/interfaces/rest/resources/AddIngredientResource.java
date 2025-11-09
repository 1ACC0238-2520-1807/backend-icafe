package com.synccafe.icafe.product.interfaces.rest.resources;

public record AddIngredientResource(
        Long supplyItemId,
        double quantity
) {
}
