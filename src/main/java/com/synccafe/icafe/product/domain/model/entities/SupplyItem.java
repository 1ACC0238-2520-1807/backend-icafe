package com.synccafe.icafe.product.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class SupplyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long providerId;
    private Long branchId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String unit;
    @Getter
    @Setter
    private double unitPrice;
    @Getter
    @Setter
    private double stock;
    @Getter
    @Setter
    private Date buyDate;
    @Getter
    @Setter
    private Date expiredDate;

    protected SupplyItem() {}

    public SupplyItem(Long providerId, Long branchId, String name, String unit, double unitPrice, double stock) {
        this.providerId = providerId;
        this.branchId = branchId;
        this.name = name;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }
}
