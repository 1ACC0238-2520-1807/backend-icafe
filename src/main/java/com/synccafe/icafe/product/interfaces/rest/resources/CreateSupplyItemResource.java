package com.synccafe.icafe.product.interfaces.rest.resources;

import java.util.Date;

public record CreateSupplyItemResource(
        Long providerId,
        Long branchId,
        String name,
        String unit,
        double unitPrice,
        double stock,
        Date expiredDate
) {
}
