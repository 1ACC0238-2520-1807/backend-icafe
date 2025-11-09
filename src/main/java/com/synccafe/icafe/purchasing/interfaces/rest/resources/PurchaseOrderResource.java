package com.synccafe.icafe.purchasing.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synccafe.icafe.purchasing.domain.model.valueobjects.PurchaseOrderStatus;

import java.time.LocalDate;

public record PurchaseOrderResource(
    Long id,
    Long branchId,
    Long providerId,
    String providerName,
    String providerEmail,
    String providerPhone,
    String providerRuc,
    Long supplyItemId,
    Double unitPrice,
    Double quantity,
    Double totalAmount,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate purchaseDate,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate expirationDate,
    PurchaseOrderStatus status,
    String notes
) {}