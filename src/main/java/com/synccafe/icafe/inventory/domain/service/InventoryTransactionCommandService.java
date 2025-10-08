package com.synccafe.icafe.inventory.domain.service;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterInventoryTransactionCommand;
import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;

import java.util.Optional;

public interface InventoryTransactionCommandService {
    Optional<InventoryTransaction> handle(RegisterInventoryTransactionCommand command);
}