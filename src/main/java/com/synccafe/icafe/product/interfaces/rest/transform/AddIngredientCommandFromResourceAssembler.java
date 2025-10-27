package com.synccafe.icafe.product.interfaces.rest.transform;

import com.synccafe.icafe.product.domain.model.commands.AddIngredientCommand;

import com.synccafe.icafe.product.interfaces.rest.resources.AddIngredientResource;


public class AddIngredientCommandFromResourceAssembler {
    public static AddIngredientCommand toCommandFromResource(Long productId,AddIngredientResource resource) {
        return new AddIngredientCommand(
                productId,
                resource.supplyItemId(),
                resource.quantity()
        );
    }
}
