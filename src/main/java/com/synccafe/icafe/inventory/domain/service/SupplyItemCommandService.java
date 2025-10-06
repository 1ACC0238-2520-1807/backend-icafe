package com.synccafe.icafe.inventory.domain.service;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.commands.RegisterSupplyItemCommand;
import com.synccafe.icafe.inventory.domain.model.commands.RemoveSupplyItemCommand;
import com.synccafe.icafe.inventory.domain.model.commands.UpdateSupplyItemCommand;

import java.util.Optional;

public interface SupplyItemCommandService {
    Optional<SupplyItem> handle(RegisterSupplyItemCommand command);
    Optional<SupplyItem> handle(UpdateSupplyItemCommand command);
    void handle(RemoveSupplyItemCommand command);
}