package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.CreateProductCommand;
import com.synccafe.icafe.product.interfaces.rest.resources.CreateProductResource;

import java.util.List;
import java.util.stream.Collectors;

public class CreateProductCommandFromResourceAssembler {

    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        return new CreateProductCommand(
                resource.branchId(),
                resource.name(),
                resource.costPrice(),
                resource.profitMargin()
        );
    }
}