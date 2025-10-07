package com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.model.valueobjects.OwnerId;
import com.synccafe.icafe.product.domain.model.valueobjects.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by owner and branch
    List<Product> findByOwnerIdAndBranchId(OwnerId ownerId, BranchId branchId);

    // Find active products by owner and branch
    List<Product> findByOwnerIdAndBranchIdAndStatus(OwnerId ownerId, BranchId branchId, ProductStatus status);

    // Find products by category
    List<Product> findByOwnerIdAndBranchIdAndCategoryIgnoreCase(OwnerId ownerId, BranchId branchId, String category);

    // Check if product name exists (case insensitive)
    boolean existsByNameIgnoreCaseAndOwnerIdAndBranchId(String name, OwnerId ownerId, BranchId branchId);

    // Check if product name exists excluding specific ID (for updates)
    boolean existsByNameIgnoreCaseAndOwnerIdAndBranchIdAndIdNot(String name, OwnerId ownerId, BranchId branchId, Long excludeId);

    // Find product by name (case insensitive)
    Optional<Product> findByNameIgnoreCaseAndOwnerIdAndBranchId(String name, OwnerId ownerId, BranchId branchId);

    // Custom query to find products with specific direct item
    @Query("SELECT p FROM Product p WHERE p.directItem.itemId = :itemId AND p.ownerId = :ownerId AND p.branchId = :branchId")
    List<Product> findByDirectItemId(@Param("itemId") Long itemId, @Param("ownerId") OwnerId ownerId, @Param("branchId") BranchId branchId);

    // Custom query to find products that use specific item in components
    @Query("SELECT DISTINCT p FROM Product p JOIN p.components c WHERE c.itemId = :itemId AND p.ownerId = :ownerId AND p.branchId = :branchId")
    List<Product> findByComponentItemId(@Param("itemId") Long itemId, @Param("ownerId") OwnerId ownerId, @Param("branchId") BranchId branchId);

    // Find products by status
    List<Product> findByOwnerIdAndBranchIdAndStatusOrderByNameAsc(OwnerId ownerId, BranchId branchId, ProductStatus status);

    // Count products by owner and branch
    long countByOwnerIdAndBranchId(OwnerId ownerId, BranchId branchId);

    // Count active products by owner and branch
    long countByOwnerIdAndBranchIdAndStatus(OwnerId ownerId, BranchId branchId, ProductStatus status);
}