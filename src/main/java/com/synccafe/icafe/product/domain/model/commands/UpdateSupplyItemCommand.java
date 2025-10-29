package com.synccafe.icafe.product.domain.model.commands;

import java.util.Date;

public record UpdateSupplyItemCommand (Long supplyItemId, String name, String unit, double unitPrice, double stock, Date expiredDate){}