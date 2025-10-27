package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.commands.*;

import java.util.Optional;

public interface ProductCommandService {
    Optional<Product> handle(CreateProductCommand command);
    Optional<Product> handle(Long productId, UpdateProductCommand command);
    Optional<Product> handle(Long productId,ActivateProductCommand command);
    Optional<Product> handle(Long productId,ArchiveProductCommand command);
    Optional<Product> handle(AddIngredientCommand command);
}