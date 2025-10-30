package com.synccafe.icafe.purchasing.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record SupplierContact(
    @Column(name = "supplier_name", nullable = false, length = 100)
    String name,
    
    @Column(name = "supplier_email", length = 100)
    String email,
    
    @Column(name = "supplier_phone", length = 20)
    String phone
) {
    public SupplierContact {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío");
        }
        if (email != null && !email.isBlank() && !email.contains("@")) {
            throw new IllegalArgumentException("El email del proveedor debe tener un formato válido");
        }
    }
}