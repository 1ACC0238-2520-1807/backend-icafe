package com.synccafe.icafe.sales.domain.model.commands;

public record CreateSaleItemCommand(
    Long productId,
    Double quantity,
    Double unitPrice
) {
    public CreateSaleItemCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID cannot be null or negative");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be null or negative");
        }
    }
}