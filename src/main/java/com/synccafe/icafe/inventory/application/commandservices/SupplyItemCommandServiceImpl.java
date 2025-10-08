package com.synccafe.icafe.inventory.application.commandservices;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyManagement;
import com.synccafe.icafe.inventory.domain.model.commands.RegisterSupplyItemCommand;
import com.synccafe.icafe.inventory.domain.model.commands.RemoveSupplyItemCommand;
import com.synccafe.icafe.inventory.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.ReorderPoint;
import com.synccafe.icafe.inventory.domain.service.SupplyItemCommandService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.SupplyItemRepository;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.SupplyManagementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SupplyItemCommandServiceImpl implements SupplyItemCommandService {

    private final SupplyItemRepository supplyItemRepository;
    private final SupplyManagementRepository supplyManagementRepository;

    public SupplyItemCommandServiceImpl(SupplyItemRepository supplyItemRepository, 
                                      SupplyManagementRepository supplyManagementRepository) {
        this.supplyItemRepository = supplyItemRepository;
        this.supplyManagementRepository = supplyManagementRepository;
    }

    @Override
    @Transactional
    public Optional<SupplyItem> handle(RegisterSupplyItemCommand command) {
        // Verify that supply management exists
        Optional<SupplyManagement> supplyManagement = supplyManagementRepository.findById(command.supplyManagementId());
        if (supplyManagement.isEmpty()) {
            throw new IllegalArgumentException("Supply Management not found with id: " + command.supplyManagementId());
        }

        // Check if supply item with same name already exists
        if (supplyItemRepository.existsByNombre(command.nombre())) {
            throw new IllegalArgumentException("Supply item with name '" + command.nombre() + "' already exists");
        }

        // Create quantity and reorder point
        Quantity initialQuantity = new Quantity(command.cantidadInicial(), command.unidadMedida());
        ReorderPoint reorderPoint = new ReorderPoint(command.puntoDeReorden());

        // Create supply item
        SupplyItem supplyItem = new SupplyItem(
            command.nombre(),
            command.unidadMedida(),
            initialQuantity,
            reorderPoint
        );

        // Add to supply management
        supplyManagement.get().addSupplyItem(supplyItem);

        // Save
        SupplyItem savedSupplyItem = supplyItemRepository.save(supplyItem);
        return Optional.of(savedSupplyItem);
    }

    @Override
    @Transactional
    public Optional<SupplyItem> handle(UpdateSupplyItemCommand command) {
        Optional<SupplyItem> existingSupplyItem = supplyItemRepository.findById(command.supplyItemId());
        if (existingSupplyItem.isEmpty()) {
            throw new IllegalArgumentException("Supply item not found with id: " + command.supplyItemId());
        }

        SupplyItem supplyItem = existingSupplyItem.get();
        
        // Update reorder point
        ReorderPoint newReorderPoint = new ReorderPoint(command.puntoDeReorden());
        supplyItem.updateReorderPoint(newReorderPoint);

        // Save updated supply item
        SupplyItem savedSupplyItem = supplyItemRepository.save(supplyItem);
        return Optional.of(savedSupplyItem);
    }

    @Override
    @Transactional
    public void handle(RemoveSupplyItemCommand command) {
        Optional<SupplyItem> existingSupplyItem = supplyItemRepository.findById(command.supplyItemId());
        if (existingSupplyItem.isEmpty()) {
            throw new IllegalArgumentException("Supply item not found with id: " + command.supplyItemId());
        }

        SupplyItem supplyItem = existingSupplyItem.get();
        
        // Remove from supply management
        if (supplyItem.getSupplyManagement() != null) {
            supplyItem.getSupplyManagement().removeSupplyItem(supplyItem);
        }

        // Delete supply item
        supplyItemRepository.delete(supplyItem);
    }
}