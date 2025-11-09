package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.UpdateProductCommand;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateProductResource;


public class UpdateProductCommandFromResourceAssembler {

    public static UpdateProductCommand toCommandFromResource(UpdateProductResource resource) {
        return new UpdateProductCommand(
                resource.name(),
                resource.costPrice(),
                resource.profitMargin()
        );
    }
}