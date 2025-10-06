package com.synccafe.icafe.inventory.domain.model.valueobjects;

public enum UnitMeasureType {
    GRAMOS("g"),
    KILOGRAMOS("kg"),
    LITROS("l"),
    MILILITROS("ml"),
    UNIDADES("unidades");

    private final String symbol;

    UnitMeasureType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}