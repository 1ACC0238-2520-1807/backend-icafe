package com.synccafe.icafe.product.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;


public record UpdateProductResource(
        String name,
        double costPrice,
        double profitMargin
) {
}