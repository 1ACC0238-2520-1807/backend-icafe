package com.synccafe.icafe.inventory.interfaces.rest.resources;

public record CurrentStockResource(
    Long branchId,
    Long supplyItemId,
    Double currentStock
) {}