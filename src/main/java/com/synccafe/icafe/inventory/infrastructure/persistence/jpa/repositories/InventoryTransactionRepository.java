package com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.inventory.domain.model.entities.InventoryTransaction;
import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    
    List<InventoryTransaction> findByTipoMovimiento(TransactionType tipoMovimiento);
    
    @Query("SELECT it FROM InventoryTransaction it WHERE it.supplyManagement.id = :supplyManagementId ORDER BY it.fecha DESC")
    List<InventoryTransaction> findBySupplyManagementIdOrderByDateDesc(@Param("supplyManagementId") Long supplyManagementId);
    
    @Query("SELECT it FROM InventoryTransaction it WHERE it.fecha BETWEEN :startDate AND :endDate ORDER BY it.fecha DESC")
    List<InventoryTransaction> findByDateRangeOrderByDateDesc(@Param("startDate") LocalDateTime startDate, 
                                                             @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT it FROM InventoryTransaction it WHERE it.referencia LIKE %:reference%")
    List<InventoryTransaction> findByReferenceContaining(@Param("reference") String reference);
}