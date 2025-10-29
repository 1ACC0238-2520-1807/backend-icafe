package com.synccafe.icafe.inventory.interfaces.rest.resources;

public record CurrentStockResource(
    Long productId,
    Double currentStock
) {}