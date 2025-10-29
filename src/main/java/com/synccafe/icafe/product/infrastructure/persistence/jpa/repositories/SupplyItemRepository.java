package com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories;


import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
    boolean existsByName(String name);
}

