package com.synccafe.icafe.product.interfaces.rest.resources;



public record CreateProductResource(
        Long branchId,
        String name,
        double costPrice,
        double profitMargin
){
}