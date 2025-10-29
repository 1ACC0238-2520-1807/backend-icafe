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
        return Optional.empty();
    }

    @Override
    public Optional<SupplyItem> handle(UpdateSupplyItemCommand command) {
        return Optional.empty();
    }

    @Override
    public Optional<SupplyItem> handle(DeleteSupplyItemCommand command) {
        return Optional.empty();
    }
}
