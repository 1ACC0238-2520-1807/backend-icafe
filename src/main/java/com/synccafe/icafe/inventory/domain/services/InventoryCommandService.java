package com.synccafe.icafe.inventory.domain.services;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterStockMovementCommand;

public interface InventoryCommandService {
    void handle(RegisterStockMovementCommand command);
}