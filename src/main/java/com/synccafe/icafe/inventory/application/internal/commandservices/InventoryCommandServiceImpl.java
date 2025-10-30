package com.synccafe.icafe.inventory.application.internal.commandservices;

import com.synccafe.icafe.inventory.domain.model.commands.RegisterStockMovementCommand;
import com.synccafe.icafe.inventory.domain.model.entities.ProductStock;
import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.inventory.domain.services.InventoryCommandService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.ProductStockRepository;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryCommandServiceImpl implements InventoryCommandService {
    
    private final StockMovementRepository stockMovementRepository;
    private final ProductStockRepository productStockRepository;
    
    public InventoryCommandServiceImpl(StockMovementRepository stockMovementRepository, 
                                     ProductStockRepository productStockRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productStockRepository = productStockRepository;
    }
    
    @Override
    @Transactional
    public void handle(RegisterStockMovementCommand command) {
        // Crear y guardar el movimiento
        StockMovement movement = new StockMovement(
            command.supplyItemId(),
            command.branchId(),
            command.type(),
            command.quantity(),
            command.origin()
        );
        stockMovementRepository.save(movement);
        
        // Actualizar el stock del insumo (SupplyItem) por sucursal
        ProductStock productStock = productStockRepository
            .findBySupplyItemIdAndBranchId(command.supplyItemId(), new com.synccafe.icafe.product.domain.model.valueobjects.BranchId(command.branchId()))
            .orElse(new ProductStock(command.supplyItemId(), command.branchId()));
        
        switch (command.type()) {
            case ENTRADA -> productStock.addStock(command.quantity());
            case SALIDA -> productStock.removeStock(command.quantity());
        }
        
        productStockRepository.save(productStock);
    }
}