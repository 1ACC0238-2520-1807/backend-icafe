package com.synccafe.icafe.product.domain.model.valueobjects;

public enum UnitType {
    GRAMS("g", "Grams"),
    MILLILITERS("ml", "Milliliters"),
    UNITS("unit", "Units");

    private final String symbol;
    private final String description;

    UnitType(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }
}