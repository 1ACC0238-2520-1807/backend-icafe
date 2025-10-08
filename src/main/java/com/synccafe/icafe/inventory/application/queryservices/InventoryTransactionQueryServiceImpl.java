package com.synccafe.icafe.inventory.application.queryservices;

import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;
import com.synccafe.icafe.inventory.domain.model.queries.GetAllInventoryTransactionsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetInventoryTransactionByIdQuery;
import com.synccafe.icafe.inventory.domain.service.InventoryTransactionQueryService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.InventoryTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryTransactionQueryServiceImpl implements InventoryTransactionQueryService {

    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryTransactionQueryServiceImpl(InventoryTransactionRepository inventoryTransactionRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
    }

    @Override
    public List<InventoryTransaction> handle(GetAllInventoryTransactionsQuery query) {
        return inventoryTransactionRepository.findAll();
    }

    @Override
    public Optional<InventoryTransaction> handle(GetInventoryTransactionByIdQuery query) {
        return inventoryTransactionRepository.findById(query.inventoryTransactionId());
    }
}