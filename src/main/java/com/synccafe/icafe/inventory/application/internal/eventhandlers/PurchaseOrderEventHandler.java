package com.synccafe.icafe.inventory.application.internal.eventhandlers;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterStockMovementCommand;
import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.inventory.domain.services.InventoryCommandService;
import com.synccafe.icafe.purchasing.domain.model.events.PurchaseOrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PurchaseOrderEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderEventHandler.class);
    private final InventoryCommandService inventoryCommandService;

    public PurchaseOrderEventHandler(InventoryCommandService inventoryCommandService) {
        this.inventoryCommandService = inventoryCommandService;
    }

    @EventListener
    @Transactional
    public void handlePurchaseOrderCreated(PurchaseOrderCreatedEvent event) {
        LOGGER.info("Processing PurchaseOrderCreatedEvent for purchase order ID: {} in branch: {}", 
                   event.purchaseOrderId(), event.branchId());
        
        try {
            // Crear comando para registrar movimiento de entrada de stock
            var registerStockMovementCommand = new RegisterStockMovementCommand(
                event.supplyItemId(),
                event.branchId(),
                StockMovement.MovementType.ENTRADA,
                event.quantity(),
                "Compra - Orden #" + event.purchaseOrderId()
            );
            
            // Registrar el movimiento de entrada en el inventario
            inventoryCommandService.handle(registerStockMovementCommand);
            
            LOGGER.info("Stock movement registered successfully for purchase order ID: {} - Supply Item: {} - Quantity: {}", 
                       event.purchaseOrderId(), event.supplyItemId(), event.quantity());
            
        } catch (Exception e) {
            LOGGER.error("Error processing PurchaseOrderCreatedEvent for purchase order ID: {}", 
                        event.purchaseOrderId(), e);
            throw e; // Re-throw to ensure transaction rollback
        }
    }
}