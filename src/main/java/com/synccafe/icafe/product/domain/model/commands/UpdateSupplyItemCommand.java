package com.synccafe.icafe.product.domain.model.commands;

import java.util.Date;

public record UpdateSupplyItemCommand (Long supplyItemId, String name, double unitPrice, double stock, Date expiredDate){}