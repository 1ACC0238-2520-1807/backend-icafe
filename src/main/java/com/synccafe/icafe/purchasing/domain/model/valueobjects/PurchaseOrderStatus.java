package com.synccafe.icafe.purchasing.domain.model.valueobjects;

public enum PurchaseOrderStatus {
    PENDING("Orden pendiente de confirmación"),
    CONFIRMED("Orden confirmada, esperando recepción"),
    COMPLETED("Orden completada, mercancía recibida"),
    CANCELLED("Orden cancelada");

    private final String description;

    PurchaseOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}