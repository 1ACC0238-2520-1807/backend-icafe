package com.synccafe.icafe.purchasing.domain.services;

import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.domain.model.queries.GetPurchaseOrderByIdQuery;
import com.synccafe.icafe.purchasing.domain.model.queries.GetPurchaseOrdersByBranchQuery;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderQueryService {
    Optional<PurchaseOrder> handle(GetPurchaseOrderByIdQuery query);
    List<PurchaseOrder> handle(GetPurchaseOrdersByBranchQuery query);
}