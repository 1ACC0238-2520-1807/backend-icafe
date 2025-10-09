package com.synccafe.icafe.product.domain.model.aggregates;

import com.synccafe.icafe.product.domain.model.events.*;
import com.synccafe.icafe.product.domain.model.valueobjects.*;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Product extends AuditableAbstractAggregateRoot<Product> {

    @Embedded
    @AttributeOverride(name = "ownerId", column = @Column(name = "owner_id"))
    private OwnerId ownerId;

    @Embedded
    @AttributeOverride(name = "branchId", column = @Column(name = "branch_id"))
    private BranchId branchId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    @Column(nullable = false)
    private Integer portions;

    @Column(length = 1000)
    private String steps;

    @Column(nullable = false)
    private Integer version;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "itemId", column = @Column(name = "direct_item_id")),
        @AttributeOverride(name = "portionFactor", column = @Column(name = "direct_portion_factor")),
        @AttributeOverride(name = "unit", column = @Column(name = "direct_unit"))
    })
    private DirectItemSpec directItem;

    @ElementCollection
    @CollectionTable(name = "product_components", joinColumns = @JoinColumn(name = "product_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "itemId", column = @Column(name = "item_id")),
        @AttributeOverride(name = "qtyPerPortion", column = @Column(name = "qty_per_portion")),
        @AttributeOverride(name = "unit", column = @Column(name = "unit"))
    })
    private List<RecipeItem> components = new ArrayList<>();

    protected Product() {}

    public Product(OwnerId ownerId, BranchId branchId, String name, String category, 
                   ProductType type, Integer portions, String steps) {
        this();
        this.ownerId = ownerId;
        this.branchId = branchId;
        this.name = name;
        this.category = category;
        this.type = type;
        this.portions = portions;
        this.steps = steps;
        this.version = 1;
        this.status = ProductStatus.ACTIVE;
        
        // Note: validateInvariants() will be called after setting directItem/components
        // in ProductCommandServiceImpl to ensure proper validation
        
        // Emit domain event
        registerEvent(new ProductCreated(
            getId(), 
            ownerId.ownerId(), 
            branchId.branchId(), 
            name, 
            category, 
            type, 
            portions, 
            steps, 
            version, 
            directItem, 
            new ArrayList<>(components)
        ));
    }

    public void updateBasicInfo(String name, String category, String steps) {
        Set<String> changedFields = new HashSet<>();
        
        if (name != null && !name.trim().isEmpty() && !this.name.equals(name.trim())) {
            this.name = name.trim();
            changedFields.add("name");
        }
        
        if ((category == null && this.category != null) || 
            (category != null && !category.equals(this.category))) {
            this.category = category;
            changedFields.add("category");
        }
        
        if ((steps == null && this.steps != null) || 
            (steps != null && !steps.equals(this.steps))) {
            this.steps = steps;
            changedFields.add("steps");
        }
        
        validateInvariants();
        
        if (!changedFields.isEmpty()) {
            registerEvent(new ProductUpdated(
                getId(),
                ownerId.ownerId(),
                branchId.branchId(),
                this.name,
                changedFields,
                version,
                directItem,
                new ArrayList<>(components)
            ));
        }
    }

    public void updatePortions(Integer portions) {
        if (portions == null || portions <= 0) {
            throw new IllegalArgumentException("Las porciones deben ser mayor a cero");
        }
        
        boolean shouldIncrementVersion = !this.portions.equals(portions);
        this.portions = portions;
        
        if (shouldIncrementVersion) {
            incrementVersion();
            
            Set<String> changedFields = new HashSet<>();
            changedFields.add("portions");
            
            registerEvent(new ProductUpdated(
                getId(),
                ownerId.ownerId(),
                branchId.branchId(),
                this.name,
                changedFields,
                version,
                directItem,
                new ArrayList<>(components)
            ));
        }
    }

    public void setDirectItem(DirectItemSpec directItem) {
        if (this.type != ProductType.SIMPLE) {
            throw new IllegalArgumentException("Solo los productos SIMPLE pueden tener un item directo");
        }
        
        this.directItem = directItem;
        this.components.clear();
        incrementVersion();
        
        Set<String> changedFields = new HashSet<>();
        changedFields.add("directItem");
        
        registerEvent(new ProductUpdated(
            getId(),
            ownerId.ownerId(),
            branchId.branchId(),
            this.name,
            changedFields,
            version,
            directItem,
            new ArrayList<>(components)
        ));
    }

    public void setComponents(List<RecipeItem> components) {
        if (this.type != ProductType.COMPOSED) {
            throw new IllegalArgumentException("Solo los productos COMPOSED pueden tener componentes");
        }
        
        if (components == null || components.isEmpty()) {
            throw new IllegalArgumentException("Los productos COMPOSED deben tener al menos un componente");
        }
        
        this.components.clear();
        this.components.addAll(components);
        this.directItem = null;
        incrementVersion();
        
        Set<String> changedFields = new HashSet<>();
        changedFields.add("components");
        
        registerEvent(new ProductUpdated(
            getId(),
            ownerId.ownerId(),
            branchId.branchId(),
            this.name,
            changedFields,
            version,
            directItem,
            new ArrayList<>(this.components)
        ));
    }

    public void archive() {
        this.status = ProductStatus.ARCHIVED;
        
        registerEvent(new ProductArchived(
            getId(),
            ownerId.ownerId(),
            branchId.branchId(),
            this.name
        ));
    }

    public void activate() {
        this.status = ProductStatus.ACTIVE;
        
        registerEvent(new ProductActivated(
            getId(),
            ownerId.ownerId(),
            branchId.branchId(),
            this.name
        ));
    }

    public boolean isActive() {
        return this.status == ProductStatus.ACTIVE;
    }

    public boolean isArchived() {
        return this.status == ProductStatus.ARCHIVED;
    }

    private void incrementVersion() {
        this.version++;
    }

    public void validateInvariants() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        
        if (portions == null || portions <= 0) {
            throw new IllegalArgumentException("Las porciones deben ser mayor a cero");
        }
        
        if (type == ProductType.SIMPLE && directItem == null) {
            throw new IllegalArgumentException("Los productos SIMPLE deben tener un item directo");
        }
        
        if (type == ProductType.COMPOSED && (components == null || components.isEmpty())) {
            throw new IllegalArgumentException("Los productos COMPOSED deben tener al menos un componente");
        }
        
        if (type == ProductType.SIMPLE && !components.isEmpty()) {
            throw new IllegalArgumentException("Los productos SIMPLE no pueden tener componentes");
        }
        
        if (type == ProductType.COMPOSED && directItem != null) {
            throw new IllegalArgumentException("Los productos COMPOSED no pueden tener un item directo");
        }
    }

    public Long getOwnerIdValue() {
        return ownerId.ownerId();
    }

    public Long getBranchIdValue() {
        return branchId.branchId();
    }

    public void delete() {
        registerEvent(new ProductDeleted(
            getId(),
            ownerId.ownerId(),
            branchId.branchId(),
            this.name
        ));
    }
}