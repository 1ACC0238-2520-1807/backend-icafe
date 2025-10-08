package com.synccafe.icafe.inventory.domain.service;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyManagement;
import com.synccafe.icafe.inventory.domain.model.valueobjects.Quantity;
import com.synccafe.icafe.inventory.domain.model.valueobjects.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class StockReplenishmentService {

    public void reabastecer(SupplyManagement supplyManagement, SupplyItem supplyItem, Quantity quantity, String reference) {
        if (supplyItem == null) {
            throw new IllegalArgumentException("El insumo no puede ser nulo");
        }
        
        if (quantity == null || quantity.valor() <= 0) {
            throw new IllegalArgumentException("La cantidad a reabastecer debe ser mayor a cero");
        }

        // Verify unit measure consistency
        if (!quantity.unidadMedida().equals(supplyItem.getUnidadMedida())) {
            throw new IllegalArgumentException("La unidad de medida debe coincidir con la del insumo");
        }

        // Register the replenishment transaction
        supplyManagement.registerTransaction(
            TransactionType.ENTRADA, 
            quantity, 
            reference != null ? reference : "Reabastecimiento de insumo", 
            supplyItem
        );
    }

    public void ajustarStock(SupplyManagement supplyManagement, SupplyItem supplyItem, Quantity newQuantity, String reference) {
        if (supplyItem == null) {
            throw new IllegalArgumentException("El insumo no puede ser nulo");
        }
        
        if (newQuantity == null || newQuantity.valor() < 0) {
            throw new IllegalArgumentException("La nueva cantidad debe ser mayor o igual a cero");
        }

        // Verify unit measure consistency
        if (!newQuantity.unidadMedida().equals(supplyItem.getUnidadMedida())) {
            throw new IllegalArgumentException("La unidad de medida debe coincidir con la del insumo");
        }

        // Register the adjustment transaction
        supplyManagement.registerTransaction(
            TransactionType.AJUSTE, 
            newQuantity, 
            reference != null ? reference : "Ajuste de inventario", 
            supplyItem
        );
    }
}