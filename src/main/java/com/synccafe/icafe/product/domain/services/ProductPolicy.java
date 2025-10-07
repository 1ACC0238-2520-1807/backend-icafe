package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.model.valueobjects.OwnerId;
import com.synccafe.icafe.product.domain.model.valueobjects.RecipeItem;
import com.synccafe.icafe.product.domain.model.valueobjects.UnitType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductPolicy {

    /**
     * Ensures that the product name is unique within the owner's branch
     */
    public void ensureUniqueName(String name, OwnerId ownerId, BranchId branchId, 
                                Long excludeProductId, ProductRepository repository) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        
        String normalizedName = normalizeName(name);
        boolean exists = repository.existsByNameAndOwnerIdAndBranchIdAndIdNot(
            normalizedName, ownerId, branchId, excludeProductId);
        
        if (exists) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre '" + name + "' en esta sucursal");
        }
    }

    /**
     * Normalizes product name for consistency
     */
    public String normalizeName(String name) {
        if (name == null) return null;
        return name.trim().toLowerCase();
    }

    /**
     * Normalizes units to ensure consistency across recipe items
     */
    public List<RecipeItem> normalizeUnits(List<RecipeItem> components) {
        if (components == null || components.isEmpty()) {
            return components;
        }
        
        return components.stream()
            .map(this::normalizeRecipeItemUnit)
            .toList();
    }

    /**
     * Validates that all referenced items exist in the inventory
     */
    public void validateItemsExist(List<Long> itemIds, InventoryACLService inventoryService) {
        if (itemIds == null || itemIds.isEmpty()) {
            return;
        }
        
        Set<Long> existingItems = inventoryService.validateItemsExist(itemIds);
        List<Long> missingItems = itemIds.stream()
            .filter(id -> !existingItems.contains(id))
            .toList();
        
        if (!missingItems.isEmpty()) {
            throw new IllegalArgumentException("Los siguientes items no existen en el inventario: " + missingItems);
        }
    }

    /**
     * Validates that a single item exists in the inventory
     */
    public void validateItemExists(Long itemId, InventoryACLService inventoryService) {
        if (itemId == null) {
            throw new IllegalArgumentException("El ID del item no puede ser nulo");
        }
        
        if (!inventoryService.itemExists(itemId)) {
            throw new IllegalArgumentException("El item con ID " + itemId + " no existe en el inventario");
        }
    }

    /**
     * Validates business rules for product creation
     */
    public void validateProductCreation(Product product, InventoryACLService inventoryService, 
                                      ProductRepository repository) {
        // Validate name uniqueness
        ensureUniqueName(product.getName(), product.getOwnerId(), product.getBranchId(), 
                        null, repository);
        
        // Validate inventory items exist
        switch (product.getType()) {
            case SIMPLE -> {
                if (product.getDirectItem() != null) {
                    validateItemExists(product.getDirectItem().itemId(), inventoryService);
                }
            }
            case COMPOSED -> {
                if (product.getComponents() != null && !product.getComponents().isEmpty()) {
                    List<Long> itemIds = product.getComponents().stream()
                        .map(RecipeItem::itemId)
                        .toList();
                    validateItemsExist(itemIds, inventoryService);
                }
            }
        }
    }

    /**
     * Validates business rules for product updates
     */
    public void validateProductUpdate(Product product, InventoryACLService inventoryService, 
                                    ProductRepository repository) {
        // Validate name uniqueness (excluding current product)
        ensureUniqueName(product.getName(), product.getOwnerId(), product.getBranchId(), 
                        product.getId(), repository);
        
        // Validate inventory items exist (same logic as creation)
        validateProductCreation(product, inventoryService, repository);
    }

    private RecipeItem normalizeRecipeItemUnit(RecipeItem item) {
        // Convert units to standard forms if needed
        UnitType normalizedUnit = normalizeUnitType(item.unit());
        
        if (normalizedUnit != item.unit()) {
            return new RecipeItem(item.itemId(), item.qtyPerPortion(), normalizedUnit);
        }
        
        return item;
    }

    private UnitType normalizeUnitType(UnitType unit) {
        // For now, return as-is, but this could include conversions
        // e.g., converting "kg" to "g", "l" to "ml", etc.
        return unit;
    }

    // Interface for repository dependency
    public interface ProductRepository {
        boolean existsByNameAndOwnerIdAndBranchIdAndIdNot(String name, OwnerId ownerId, 
                                                         BranchId branchId, Long excludeId);
    }

    // Interface for inventory service dependency
    public interface InventoryACLService {
        boolean itemExists(Long itemId);
        Set<Long> validateItemsExist(List<Long> itemIds);
    }
}