package com.synccafe.icafe.purchasing.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.domain.model.valueobjects.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    List<PurchaseOrder> findByBranchIdOrderByPurchaseDateDesc(BranchId branchId);
    
    List<PurchaseOrder> findByBranchIdAndStatusOrderByPurchaseDateDesc(BranchId branchId, PurchaseOrderStatus status);
    
    List<PurchaseOrder> findBySupplyItemIdAndBranchIdOrderByPurchaseDateDesc(Long supplyItemId, BranchId branchId);
    
    List<PurchaseOrder> findByPurchaseDateBetweenOrderByPurchaseDateDesc(LocalDate startDate, LocalDate endDate);
    
    Optional<PurchaseOrder> findByIdAndBranchId(Long id, BranchId branchId);
}