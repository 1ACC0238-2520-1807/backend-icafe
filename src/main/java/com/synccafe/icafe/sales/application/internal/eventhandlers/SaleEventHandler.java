package com.synccafe.icafe.sales.application.internal.eventhandlers;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.entities.ProductIngredient;
import com.synccafe.icafe.product.domain.model.queries.GetProductByIdQuery;
import com.synccafe.icafe.product.domain.services.ProductQueryService;
import com.synccafe.icafe.sales.domain.model.events.SaleCreatedEvent;
import com.synccafe.icafe.sales.domain.model.valueobjects.SaleItem;
import com.synccafe.icafe.sales.infrastructure.acl.InventoryContextFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class SaleEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleEventHandler.class);
    
    private final InventoryContextFacade inventoryContextFacade;
    private final ProductQueryService productQueryService;

    public SaleEventHandler(InventoryContextFacade inventoryContextFacade,
                           ProductQueryService productQueryService) {
        this.inventoryContextFacade = inventoryContextFacade;
        this.productQueryService = productQueryService;
    }

    @EventListener
    @Transactional
    public void handleSaleCreated(SaleCreatedEvent event) {
        LOGGER.info("Processing stock movements for sale ID: {}", event.getSale().getId());
        
        try {
            // Process each sale item
            for (SaleItem saleItem : event.getSale().getItems()) {
                processProductStockMovements(
                    saleItem.getProductId(),
                    saleItem.getQuantity(),
                    event.getSale().getBranchId().branchId()
                );
            }
            
            LOGGER.info("Successfully processed stock movements for sale ID: {}", event.getSale().getId());
        } catch (Exception e) {
            LOGGER.error("Error processing stock movements for sale ID: {}", event.getSale().getId(), e);
            throw new RuntimeException("Failed to process stock movements for sale", e);
        }
    }

    private void processProductStockMovements(Long productId, Double quantitySold, Long branchId) {
        // Get the product with its ingredients
        Optional<Product> productOpt = productQueryService.handle(new GetProductByIdQuery(productId));
        
        if (productOpt.isEmpty()) {
            LOGGER.warn("Product with ID {} not found", productId);
            return;
        }

        Product product = productOpt.get();
        
        // Process each ingredient in the product
        for (ProductIngredient ingredient : product.getIngredients()) {
            Double totalQuantityNeeded = ingredient.getQuantity() * quantitySold;
            
            // Register stock movement for the supply item using the facade
            String saleReference = "Product: " + product.getName() + " (ID: " + productId + ")";
            inventoryContextFacade.registerSaleStockExit(
                ingredient.getSupplyItem().getId(),
                branchId,
                totalQuantityNeeded,
                saleReference
            );
            
            LOGGER.debug("Registered stock movement for supply item ID: {}, quantity: {}", 
                        ingredient.getSupplyItem().getId(), totalQuantityNeeded);
        }
    }
}