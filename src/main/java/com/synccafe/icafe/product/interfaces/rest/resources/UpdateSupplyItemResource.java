package com.synccafe.icafe.product.interfaces.rest.resources;

import java.util.Date;

public record UpdateSupplyItemResource(
        String name,
        double unitPrice,
        double stock,
        Date expiredDate
) {
}
