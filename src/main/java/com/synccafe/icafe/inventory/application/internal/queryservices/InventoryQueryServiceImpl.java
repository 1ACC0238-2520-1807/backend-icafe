package com.synccafe.icafe.inventory.application.internal.queryservices;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.inventory.domain.model.queries.GetCurrentStockQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetStockMovementsByBranchQuery;
import com.synccafe.icafe.inventory.domain.services.InventoryQueryService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.ProductStockRepository;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final ProductStockRepository productStockRepository;
    private final StockMovementRepository stockMovementRepository;

    public InventoryQueryServiceImpl(ProductStockRepository productStockRepository, StockMovementRepository stockMovementRepository) {
        this.productStockRepository = productStockRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public Double handle(GetCurrentStockQuery query) {
        return productStockRepository.findBySupplyItemIdAndBranchId(query.supplyItemId(), new com.synccafe.icafe.product.domain.model.valueobjects.BranchId(query.branchId()))
                .map(productStock -> productStock.getCurrentStock())
                .orElse(0.0);
    }

    @Override
    public List<StockMovement> handle(GetStockMovementsByBranchQuery query) {
        return stockMovementRepository.findAllByBranchId(query.branchId());
    }
}