package com.synccafe.icafe.product.domain.model.commands;

import java.util.Date;

public record CreateSupplyItemCommand (Long providerId, Long branchId, String name, String unit, double unitPrice, double stock, Date expiredDate) {

}
