package com.synccafe.icafe.inventory.domain.services;

import com.synccafe.icafe.inventory.domain.model.queries.GetCurrentStockQuery;

public interface InventoryQueryService {
    Double handle(GetCurrentStockQuery query);
}