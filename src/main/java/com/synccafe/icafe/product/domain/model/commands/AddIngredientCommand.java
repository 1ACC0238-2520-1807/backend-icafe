package com.synccafe.icafe.product.domain.model.commands;

import com.synccafe.icafe.product.domain.model.entities.SupplyItem;

public record AddIngredientCommand(
        Long productId,
        SupplyItem supplyItem,
        double quantity
) {
}
