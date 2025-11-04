package com.synccafe.icafe.sales.interfaces.rest.resources;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResource(
    Long id,
    Long customerId,
    Long branchId,
    List<SaleItemResource> items,
    Double totalAmount,
    LocalDateTime saleDate,
    String status,
    String notes
) {
}