package com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
    
    boolean existsByNombre(String nombre);
    
    Optional<SupplyItem> findByNombre(String nombre);
    
    @Query("SELECT s FROM SupplyItem s WHERE s.cantidadActual.valor <= s.puntoDeReorden.cantidadMinima")
    List<SupplyItem> findLowStockItems();
    
    @Query("SELECT s FROM SupplyItem s WHERE s.supplyManagement.id = :supplyManagementId")
    List<SupplyItem> findBySupplyManagementId(@Param("supplyManagementId") Long supplyManagementId);
}