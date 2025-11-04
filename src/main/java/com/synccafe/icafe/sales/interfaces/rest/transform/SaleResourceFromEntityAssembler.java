package com.synccafe.icafe.sales.interfaces.rest.transform;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.interfaces.rest.resources.SaleItemResource;
import com.synccafe.icafe.sales.interfaces.rest.resources.SaleResource;

import java.util.List;

public class SaleResourceFromEntityAssembler {

    public static SaleResource toResourceFromEntity(Sale sale) {
        List<SaleItemResource> itemResources = sale.getItems().stream()
            .map(item -> new SaleItemResource(
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice().amount(),
                item.getSubtotal().amount()
            ))
            .toList();

        return new SaleResource(
            sale.getId(),
            sale.getCustomerId().customerId(),
            sale.getBranchId().branchId(),
            itemResources,
            sale.getTotalAmount().amount(),
            sale.getSaleDate(),
            sale.getStatus().name(),
            sale.getNotes()
        );
    }
}