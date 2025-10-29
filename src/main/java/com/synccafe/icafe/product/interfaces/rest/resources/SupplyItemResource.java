package com.synccafe.icafe.product.interfaces.rest.resources;

import java.util.Date;

public record SupplyItemResource(
        Long id,
        Long providerId,
        Long branchId,
        String name,
        String unit,
        double unitPrice,
        double stock,
        Date buyDate,
        Date expiredDate
) {
}
