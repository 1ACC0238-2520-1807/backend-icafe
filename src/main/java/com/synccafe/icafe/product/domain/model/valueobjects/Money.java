package com.synccafe.icafe.product.domain.model.valueobjects;


import jakarta.persistence.Embeddable;

@Embeddable
public record Money(double amount) {
    public Money {
        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
    }
}
