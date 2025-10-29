package com.synccafe.icafe.product.interfaces.rest.resources;

import java.util.Date;

public record UpdateSupplyItemResource(
        Long supplyItemId,
        String name,
        String unit,
        double unitPrice,
        double stock,
        Date expiredDate
) {
}
