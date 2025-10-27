package com.synccafe.icafe.product.domain.model.commands;

public record AddIngredientCommand(
        Long productId,
        Long supplyItemId,
        double quantity
) {
}
