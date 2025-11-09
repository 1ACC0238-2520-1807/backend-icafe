package com.synccafe.icafe.purchasing.application.internal.queryservices;

import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.domain.model.queries.GetPurchaseOrderByIdQuery;
import com.synccafe.icafe.purchasing.domain.model.queries.GetPurchaseOrdersByBranchQuery;
import com.synccafe.icafe.purchasing.domain.services.PurchaseOrderQueryService;
import com.synccafe.icafe.purchasing.infrastructure.persistence.jpa.repositories.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderQueryServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public Optional<PurchaseOrder> handle(GetPurchaseOrderByIdQuery query) {
        var branchId = new BranchId(query.branchId());
        return purchaseOrderRepository.findByIdAndBranchId(query.purchaseOrderId(), branchId);
    }

    @Override
    public List<PurchaseOrder> handle(GetPurchaseOrdersByBranchQuery query) {
        var branchId = new BranchId(query.branchId());
        return purchaseOrderRepository.findByBranchIdOrderByPurchaseDateDesc(branchId);
    }
}