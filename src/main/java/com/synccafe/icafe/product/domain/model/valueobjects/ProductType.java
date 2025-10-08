package com.synccafe.icafe.product.domain.model.valueobjects;

public enum ProductType {
    SIMPLE("Simple product with direct item reference"),
    COMPOSED("Composed product with recipe components");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}