package com.synccafe.icafe.inventory.domain.service;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyManagement;
import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class StockConsumptionService {

    public void consumirInsumo(SupplyManagement supplyManagement, SupplyItem supplyItem, Quantity quantity, String reference) {
        if (supplyItem == null) {
            throw new IllegalArgumentException("El insumo no puede ser nulo");
        }
        
        if (quantity == null || quantity.valor() <= 0) {
            throw new IllegalArgumentException("La cantidad a consumir debe ser mayor a cero");
        }

        // Verify that there's enough stock
        if (supplyItem.getCantidadActual().isLessThan(quantity)) {
            throw new IllegalStateException("Stock insuficiente para el insumo: " + supplyItem.getNombre());
        }

        // Register the consumption transaction
        supplyManagement.registerTransaction(
            TransactionType.SALIDA, 
            quantity, 
            reference != null ? reference : "Consumo de insumo", 
            supplyItem
        );
    }

    public boolean canConsume(SupplyItem supplyItem, Quantity quantity) {
        if (supplyItem == null || quantity == null) {
            return false;
        }
        
        return !supplyItem.getCantidadActual().isLessThan(quantity);
    }
}