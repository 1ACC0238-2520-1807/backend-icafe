package com.synccafe.icafe.sales.infrastructure.acl;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterStockMovementCommand;
import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.inventory.domain.services.InventoryCommandService;
import org.springframework.stereotype.Service;

@Service
public class InventoryContextFacade {

    private final InventoryCommandService inventoryCommandService;

    public InventoryContextFacade(InventoryCommandService inventoryCommandService) {
        this.inventoryCommandService = inventoryCommandService;
    }

    /**
     * Register a stock movement for a supply item
     */
    public void registerStockMovement(Long supplyItemId, Long branchId, 
                                    StockMovement.MovementType type, 
                                    Double quantity, String origin) {
        RegisterStockMovementCommand command = new RegisterStockMovementCommand(
            supplyItemId, branchId, type, quantity, origin
        );
        inventoryCommandService.handle(command);
    }

    /**
     * Register a stock exit for a sale
     */
    public void registerSaleStockExit(Long supplyItemId, Long branchId, 
                                    Double quantity, String saleReference) {
        registerStockMovement(supplyItemId, branchId, 
                            StockMovement.MovementType.SALIDA, 
                            quantity, "Sale: " + saleReference);
    }
}