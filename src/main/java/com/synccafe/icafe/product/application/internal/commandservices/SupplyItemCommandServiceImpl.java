package com.synccafe.icafe.product.application.internal.commandservices;

import com.synccafe.icafe.product.domain.model.commands.CreateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.DeleteSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import com.synccafe.icafe.product.domain.services.SupplyItemCommandService;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.SupplyItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplyItemCommandServiceImpl implements SupplyItemCommandService {

    private final SupplyItemRepository supplyItemRepository;

    public SupplyItemCommandServiceImpl(SupplyItemRepository supplyItemRepository) {
        this.supplyItemRepository = supplyItemRepository;
    }

    @Override
    public Optional<SupplyItem> handle(CreateSupplyItemCommand command) {
        var supplyItem = new SupplyItem(command);
        SupplyItem savedSupplyItem = supplyItemRepository.save(supplyItem);
        return Optional.of(savedSupplyItem);
    }

    @Override
    public Optional<SupplyItem> handle(Long supplyItemId, UpdateSupplyItemCommand command) {
        var optionalSupplyItem = supplyItemRepository.findById(supplyItemId);
        if (optionalSupplyItem.isEmpty()) {
            throw new IllegalArgumentException("SupplyItem with ID " + supplyItemId + " not found.");
        }
        var supplyItem = optionalSupplyItem.get();
        supplyItem.updateSupplyItem(command);
        supplyItemRepository.save(supplyItem);
        return Optional.of(supplyItem);
    }

    @Override
    public Optional<SupplyItem> handle(DeleteSupplyItemCommand command) {
        var optionalSupplyItem = supplyItemRepository.findById(command.SupplyItemId());
        if (optionalSupplyItem.isEmpty()) {
            throw new IllegalArgumentException("SupplyItem with ID " + command.SupplyItemId() + " not found.");
        }
        var supplyItem = optionalSupplyItem.get();
        supplyItemRepository.delete(supplyItem);
        return Optional.of(supplyItem);
    }
}
