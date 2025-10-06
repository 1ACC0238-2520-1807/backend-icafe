package com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyManagementRepository extends JpaRepository<SupplyManagement, Long> {
    
    Optional<SupplyManagement> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
    
    @Query("SELECT sm FROM SupplyManagement sm JOIN FETCH sm.supplyItems")
    List<SupplyManagement> findAllWithSupplyItems();
    
    @Query("SELECT sm FROM SupplyManagement sm JOIN FETCH sm.inventoryTransactions")
    List<SupplyManagement> findAllWithTransactions();
}