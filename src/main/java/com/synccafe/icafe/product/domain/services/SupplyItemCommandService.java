package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.product.domain.model.commands.CreateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.DeleteSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.entities.SupplyItem;

import java.util.Optional;

public interface SupplyItemCommandService {
    Optional<SupplyItem> handle(CreateSupplyItemCommand command);
    Optional<SupplyItem> handle(UpdateSupplyItemCommand command);
    Optional<SupplyItem> handle(DeleteSupplyItemCommand command);


}
