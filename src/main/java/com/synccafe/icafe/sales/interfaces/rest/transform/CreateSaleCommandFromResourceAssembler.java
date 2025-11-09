package com.synccafe.icafe.sales.interfaces.rest.transform;

import com.synccafe.icafe.sales.domain.model.commands.CreateSaleCommand;
import com.synccafe.icafe.sales.domain.model.commands.CreateSaleItemCommand;
import com.synccafe.icafe.sales.interfaces.rest.resources.CreateSaleResource;

import java.util.List;

public class CreateSaleCommandFromResourceAssembler {

    public static CreateSaleCommand toCommandFromResource(CreateSaleResource resource) {
        List<CreateSaleItemCommand> itemCommands = resource.items().stream()
            .map(item -> new CreateSaleItemCommand(
                item.productId(),
                item.quantity(),
                item.unitPrice()
            ))
            .toList();

        return new CreateSaleCommand(
            resource.customerId(),
            resource.branchId(),
            itemCommands,
            resource.notes()
        );
    }
}