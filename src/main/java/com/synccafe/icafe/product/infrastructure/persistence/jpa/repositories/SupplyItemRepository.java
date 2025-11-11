package com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories;


import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
    boolean existsByName(String name);
    @Query("SELECT s FROM SupplyItem s WHERE s.branchId = :branchId")
    List<SupplyItem> findAllByBranchId(@Param("branchId") BranchId branchId);

}

