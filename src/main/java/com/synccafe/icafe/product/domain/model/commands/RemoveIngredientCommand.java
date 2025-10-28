package com.synccafe.icafe.product.domain.model.commands;

public record RemoveIngredientCommand(Long productId, Long supplyItemId) {
}
