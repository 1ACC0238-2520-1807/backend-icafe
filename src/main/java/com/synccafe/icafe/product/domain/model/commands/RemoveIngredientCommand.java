package com.synccafe.icafe.product.domain.model.commands;

import com.synccafe.icafe.product.domain.model.entities.SupplyItem;

public record RemoveIngredientCommand(Long productId, Long supplyItemId) {
}
