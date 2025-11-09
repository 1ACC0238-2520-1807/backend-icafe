package com.synccafe.icafe.product.application.internal.eventhandlers;


import com.synccafe.icafe.product.domain.model.events.SupplyItemPriceUpdatedEvent;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SupplyItemEventHandler {

    private final ProductRepository productRepository;

    public SupplyItemEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener
    @Transactional
    public void handleSupplyItemPriceUpdated(SupplyItemPriceUpdatedEvent event) {
        var products = productRepository.findAll();
        products.forEach(product -> {
            boolean affected = product.getIngredients().stream()
                    .anyMatch(i -> i.getSupplyItem().getId().equals(event.supplyItemId()));
            if (affected) {
                product.recalculateCostPrice();
                productRepository.save(product);
            }
        });
    }
}