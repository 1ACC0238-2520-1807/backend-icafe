package com.synccafe.icafe.product.domain.model.entities;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_item_id")
    private SupplyItem supplyItem;
    @Getter
    @Setter
    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    protected ProductIngredient() {
    }

    public ProductIngredient(SupplyItem supplyItem , double quantity, Product product) {
        this.supplyItem = supplyItem;
        this.quantity = quantity;
        this.product = product;
    }

}
