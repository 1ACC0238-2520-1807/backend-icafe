package com.synccafe.icafe.purchasing.domain.services;

import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.domain.model.commands.CreatePurchaseOrderCommand;

import java.util.Optional;

public interface PurchaseOrderCommandService {
    Optional<PurchaseOrder> handle(CreatePurchaseOrderCommand command);
    Optional<PurchaseOrder> confirmPurchaseOrder(Long purchaseOrderId, Long branchId);
    Optional<PurchaseOrder> completePurchaseOrder(Long purchaseOrderId, Long branchId);
    Optional<PurchaseOrder> cancelPurchaseOrder(Long purchaseOrderId, Long branchId);
}