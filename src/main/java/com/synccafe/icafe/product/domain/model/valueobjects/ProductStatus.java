package com.synccafe.icafe.product.domain.model.valueobjects;

public enum ProductStatus {
    ACTIVE("Active product available for sale"),
    ARCHIVED("Archived product not available for sale");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}