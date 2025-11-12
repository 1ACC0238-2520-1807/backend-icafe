package com.synccafe.icafe.inventory.domain.services;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.inventory.domain.model.queries.GetCurrentStockQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetStockMovementsByBranchQuery;

import java.util.List;

public interface InventoryQueryService {
    Double handle(GetCurrentStockQuery query);
    List<StockMovement> handle(GetStockMovementsByBranchQuery query);
}