package com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.inventory.domain.model.entities.StockMovement;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findBySupplyItemIdAndBranchIdOrderByMovementDateDesc(Long supplyItemId, BranchId branchId);
    List<StockMovement> findAllByBranchId(Long branchId);
}