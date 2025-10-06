package com.synccafe.icafe.inventory.domain.model.valueobjects;

public enum TransactionType {
    ENTRADA("Entrada de inventario"),
    SALIDA("Salida de inventario"),
    AJUSTE("Ajuste de inventario");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}