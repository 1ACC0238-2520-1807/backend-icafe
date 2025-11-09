package com.synccafe.icafe.sales.domain.model.commands;

import java.util.List;

public record CreateSaleCommand(
    Long customerId,
    Long branchId,
    List<CreateSaleItemCommand> items,
    String notes
) {
    public CreateSaleCommand {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Customer ID cannot be null or negative");
        }
        if (branchId == null || branchId <= 0) {
            throw new IllegalArgumentException("Branch ID cannot be null or negative");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Sale must have at least one item");
        }
    }
}