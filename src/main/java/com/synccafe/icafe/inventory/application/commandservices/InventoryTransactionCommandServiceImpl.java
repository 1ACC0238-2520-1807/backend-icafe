package com.synccafe.icafe.inventory.application.commandservices;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyManagement;
import com.synccafe.icafe.inventory.domain.model.commands.RegisterInventoryTransactionCommand;
import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;
import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import com.synccafe.icafe.inventory.domain.service.InventoryTransactionCommandService;
import com.synccafe.icafe.inventory.domain.service.StockConsumptionService;
import com.synccafe.icafe.inventory.domain.service.StockReplenishmentService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.SupplyItemRepository;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.SupplyManagementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryTransactionCommandServiceImpl implements InventoryTransactionCommandService {

    private final SupplyItemRepository supplyItemRepository;
    private final SupplyManagementRepository supplyManagementRepository;
    private final StockConsumptionService stockConsumptionService;
    private final StockReplenishmentService stockReplenishmentService;

    public InventoryTransactionCommandServiceImpl(SupplyItemRepository supplyItemRepository,
                                                SupplyManagementRepository supplyManagementRepository,
                                                StockConsumptionService stockConsumptionService,
                                                StockReplenishmentService stockReplenishmentService) {
        this.supplyItemRepository = supplyItemRepository;
        this.supplyManagementRepository = supplyManagementRepository;
        this.stockConsumptionService = stockConsumptionService;
        this.stockReplenishmentService = stockReplenishmentService;
    }

    @Override
    @Transactional
    public Optional<InventoryTransaction> handle(RegisterInventoryTransactionCommand command) {
        // Find supply item
        Optional<SupplyItem> supplyItemOpt = supplyItemRepository.findById(command.supplyItemId());
        if (supplyItemOpt.isEmpty()) {
            throw new IllegalArgumentException("Supply item not found with id: " + command.supplyItemId());
        }

        SupplyItem supplyItem = supplyItemOpt.get();
        SupplyManagement supplyManagement = supplyItem.getSupplyManagement();
        
        if (supplyManagement == null) {
            throw new IllegalArgumentException("Supply item is not associated with any supply management");
        }

        // Create quantity for transaction
        Quantity quantity = new Quantity(command.cantidad(), command.unidadMedida());

        // Process transaction based on type
        InventoryTransaction transaction = null;

        switch (command.tipoMovimiento()) {
            case ENTRADA:
                stockReplenishmentService.reabastecer(supplyManagement, supplyItem, quantity, command.referencia());
                transaction = new InventoryTransaction(
                    TransactionType.ENTRADA,
                    quantity,
                    command.referencia()
                );
                break;

            case SALIDA:
                // Use consumption service for exits
                stockConsumptionService.consumirInsumo(supplyManagement, supplyItem, quantity, command.referencia());
                transaction = new InventoryTransaction(
                    TransactionType.SALIDA,
                    quantity,
                    command.referencia()
                );
                break;

            case AJUSTE:
                // Use adjustment service for adjustments
                stockReplenishmentService.ajustarStock(supplyManagement, supplyItem, quantity, command.referencia());
                transaction = new InventoryTransaction(
                    TransactionType.AJUSTE,
                    quantity,
                    command.referencia()
                );
                break;

            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + command.tipoMovimiento());
        }

        supplyManagementRepository.save(supplyManagement);
        
        return Optional.of(transaction);
    }
}