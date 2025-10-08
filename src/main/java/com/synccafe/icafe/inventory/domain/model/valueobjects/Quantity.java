package com.synccafe.icafe.inventory.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record Quantity(
        Double valor,
        @Enumerated(EnumType.STRING)
        UnitMeasureType unidadMedida
) {
    public Quantity {
        if (valor == null || valor < 0) {
            throw new IllegalArgumentException("El valor debe ser mayor o igual a cero");
        }
        if (unidadMedida == null) {
            throw new IllegalArgumentException("La unidad de medida no puede ser nula");
        }
    }

    public Quantity add(Quantity other) {
        if (!this.unidadMedida.equals(other.unidadMedida)) {
            throw new IllegalArgumentException("No se pueden sumar cantidades con diferentes unidades de medida");
        }
        return new Quantity(this.valor + other.valor, this.unidadMedida);
    }

    public Quantity subtract(Quantity other) {
        if (!this.unidadMedida.equals(other.unidadMedida)) {
            throw new IllegalArgumentException("No se pueden restar cantidades con diferentes unidades de medida");
        }
        double result = this.valor - other.valor;
        if (result < 0) {
            throw new IllegalArgumentException("El resultado no puede ser negativo");
        }
        return new Quantity(result, this.unidadMedida);
    }

    public boolean isLessThan(Quantity other) {
        if (!this.unidadMedida.equals(other.unidadMedida)) {
            throw new IllegalArgumentException("No se pueden comparar cantidades con diferentes unidades de medida");
        }
        return this.valor < other.valor;
    }
}