package com.synccafe.icafe.inventory.interfaces.rest.transform;

import com.synccafe.icafe.inventory.interfaces.rest.resources.CurrentStockResource;

public class CurrentStockResourceFromEntityAssembler {
    
    public static CurrentStockResource toResourceFromStock(Long productId, Double currentStock) {
        return new CurrentStockResource(productId, currentStock);
    }
}