package com.synccafe.icafe.purchasing.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CreatePurchaseOrderResource(
    Long branchId,
    String supplierName,
    String supplierEmail,
    String supplierPhone,
    Long supplyItemId,
    Double unitPrice,
    Double quantity,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate purchaseDate,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate expirationDate,
    String notes
) {}