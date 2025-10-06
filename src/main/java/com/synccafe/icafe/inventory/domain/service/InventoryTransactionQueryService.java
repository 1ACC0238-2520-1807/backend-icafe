package com.synccafe.icafe.inventory.domain.service;

import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;
import com.synccafe.icafe.inventory.domain.model.queries.GetAllInventoryTransactionsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetInventoryTransactionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryTransactionQueryService {
    List<InventoryTransaction> handle(GetAllInventoryTransactionsQuery query);
    Optional<InventoryTransaction> handle(GetInventoryTransactionByIdQuery query);
}