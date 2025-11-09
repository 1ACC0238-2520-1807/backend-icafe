package com.synccafe.icafe.sales.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CustomerId(Long customerId) {
    public CustomerId {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Customer ID cannot be null or negative");
        }
    }
}