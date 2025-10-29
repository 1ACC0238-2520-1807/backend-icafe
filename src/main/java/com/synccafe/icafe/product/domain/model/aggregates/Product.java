package com.synccafe.icafe.product.domain.model.aggregates;
import com.synccafe.icafe.product.domain.model.commands.AddIngredientCommand;
import com.synccafe.icafe.product.domain.model.commands.CreateProductCommand;
import com.synccafe.icafe.product.domain.model.commands.RemoveIngredientCommand;
import com.synccafe.icafe.product.domain.model.commands.UpdateProductCommand;
import com.synccafe.icafe.product.domain.model.entities.ProductIngredient;
import com.synccafe.icafe.product.domain.model.valueobjects.*;
import com.synccafe.icafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
public class Product extends AuditableAbstractAggregateRoot<Product> {

    @Getter
    @Embedded
    private BranchId branchId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "amount", column = @Column(name = "cost_price"))
    )
    private Money costPrice;
    @Getter
    @Setter
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "amount", column = @Column(name = "sale_price"))
    )
    private Money salePrice;
    @Getter
    @Setter
    private double profitMargin;
    @Getter
    @Setter
    private ProductStatus status;
    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductIngredient> ingredients =new HashSet<>();

    public Product() {
    }

    public Product(Long branchId, String name, double costPrice, double profitMargin) {
        this.branchId = new BranchId(branchId);
        this.name = name;
        this.costPrice = new Money(costPrice);
        this.profitMargin = profitMargin;
        this.salePrice = calculateSalePriceFromCostAndMargin(this.costPrice, this.profitMargin);
        this.status = ProductStatus.ACTIVE;
    }

    public Product(CreateProductCommand command){
        this.branchId = new BranchId(command.branchId());
        this.name = command.name();
        this.costPrice = new Money(command.costPrice());
        this.profitMargin = command.profitMargin();
        this.salePrice = calculateSalePriceFromCostAndMargin(this.costPrice, command.profitMargin());
        this.status = ProductStatus.ACTIVE;
    }

    public void updateProduct(UpdateProductCommand command){
        this.name = command.name();
        this.costPrice = new Money(command.costPrice());
        this.salePrice =  calculateSalePriceFromCostAndMargin(this.costPrice, command.profitMargin());
        this.profitMargin = command.profitMargin();
    }

    private Money calculateSalePriceFromCostAndMargin(Money costPrice, double profitMargin){
        double salePriceValue = costPrice.amount() + (costPrice.amount() * profitMargin / 100);
        return new Money(salePriceValue);
    }

    //activate product
    public void activateProductStatus(){
        this.status = ProductStatus.ACTIVE;
    }

    //deactivate product
    public void archivedProductStatus() {
        this.status = ProductStatus.ARCHIVED;
    }

    public void addIngredient(AddIngredientCommand command){
        ProductIngredient ingredient = new ProductIngredient(
                command.supplyItemId(),
                command.quantity(),
                this
        );
        this.ingredients.add(ingredient);
    }

    public void removeIngredient(RemoveIngredientCommand command) {
        this.ingredients.removeIf(ingredient -> {
            var supplyIdVo = ingredient.getSupplyItemId();
            if (supplyIdVo == null) return false;
            // Si el Value Object tiene el método supplyItemId() (como BranchId.branchId()),
            // obtener el Long interno y compararlo con el command.supplyItemId()
            return supplyIdVo.supplyItemId().equals(command.supplyItemId());
        });
    }



}