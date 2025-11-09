package com.synccafe.icafe.sales.interfaces.rest.resources;

public record SaleItemResource(
    Long productId,
    Double quantity,
    Double unitPrice,
    Double subtotal
) {
}