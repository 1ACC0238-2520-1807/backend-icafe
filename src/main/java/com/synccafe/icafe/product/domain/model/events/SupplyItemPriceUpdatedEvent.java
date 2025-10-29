package com.synccafe.icafe.product.domain.model.events;

public record SupplyItemPriceUpdatedEvent(
        Long supplyItemId,
        double newPrice
) {
}
