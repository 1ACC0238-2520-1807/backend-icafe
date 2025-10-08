package com.synccafe.icafe.product.application.internal.eventhandlers;

import com.synccafe.icafe.product.domain.model.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ProductEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProductEventHandler.class);

    @EventListener
    public void on(ProductCreated event) {
        logger.info("Product created: {} (ID: {}) for owner {} in branch {}", 
                   event.name(), event.productId(), event.ownerId(), event.branchId());
        
        // Here you could implement additional logic like:
        // - Sending notifications
        // - Updating projections
        // - Integrating with other bounded contexts
    }

    @EventListener
    public void on(ProductUpdated event) {
        logger.info("Product updated: {} (ID: {}) - Changed fields: {}", 
                   event.name(), event.productId(), event.changedFields());
        
        // Additional logic for product updates
    }

    @EventListener
    public void on(ProductArchived event) {
        logger.info("Product archived: {} (ID: {})", event.name(), event.productId());
        
        // Additional logic for product archiving
        // - Remove from active catalogs
        // - Update inventory references
    }

    @EventListener
    public void on(ProductActivated event) {
        logger.info("Product activated: {} (ID: {})", event.name(), event.productId());
        
        // Additional logic for product activation
        // - Add to active catalogs
        // - Validate inventory availability
    }

    @EventListener
    public void on(ProductDeleted event) {
        logger.info("Product deleted: {} (ID: {})", event.name(), event.productId());
        
        // Additional logic for product deletion
        // - Clean up references
        // - Archive related data
    }
}