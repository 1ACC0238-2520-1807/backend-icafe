package com.synccafe.icafe.sales.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Money(Double amount) {
    public Money {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount cannot be null or negative");
        }
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money multiply(Double quantity) {
        return new Money(this.amount * quantity);
    }
}