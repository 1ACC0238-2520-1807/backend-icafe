package com.synccafe.icafe.product.domain.model.entities;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.valueobjects.SupplyItemId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class ProductIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Embedded
    private SupplyItemId supplyItemId;
    @Getter
    @Setter
    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    protected ProductIngredient() {
    }

    public ProductIngredient(Long supplyItemId, double quantity, Product product) {
        this.supplyItemId = new SupplyItemId(supplyItemId);
        this.quantity = quantity;
        this.product = product;
    }

}
