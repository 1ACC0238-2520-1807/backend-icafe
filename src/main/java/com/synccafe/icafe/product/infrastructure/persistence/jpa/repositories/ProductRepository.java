package com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.model.valueobjects.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndBranchId(Long id, BranchId branchId);
    Optional<Product> findById(Long id);
    boolean existsProductByBranchId(BranchId branchId);
    boolean existsProductById(Long id);
    boolean existsProductsByName(String name);
}