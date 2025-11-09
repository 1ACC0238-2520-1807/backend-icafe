package com.synccafe.icafe.sales.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateSaleResource(
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    Long customerId,
    
    @NotNull(message = "Branch ID is required")
    @Positive(message = "Branch ID must be positive")
    Long branchId,
    
    @NotNull(message = "Items are required")
    @NotEmpty(message = "Sale must have at least one item")
    @Valid
    List<CreateSaleItemResource> items,
    
    String notes
) {
}