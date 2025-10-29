package com.synccafe.icafe.inventory.application.internal.queryservices;

import com.synccafe.icafe.inventory.domain.model.queries.GetCurrentStockQuery;
import com.synccafe.icafe.inventory.domain.services.InventoryQueryService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.ProductStockRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {
    
    private final ProductStockRepository productStockRepository;
    
    public InventoryQueryServiceImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }
    
    @Override
    public Double handle(GetCurrentStockQuery query) {
        return productStockRepository.findByProductId(query.productId())
            .map(productStock -> productStock.getCurrentStock())
            .orElse(0.0);
    }
}