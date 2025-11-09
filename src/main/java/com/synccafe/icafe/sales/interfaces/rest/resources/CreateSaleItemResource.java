package com.synccafe.icafe.sales.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSaleItemResource(
    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    Long productId,
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    Double quantity,
    
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    Double unitPrice
) {
}