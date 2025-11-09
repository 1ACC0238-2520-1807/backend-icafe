package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.queries.GetAllProductsByBranchIdQuery;
import com.synccafe.icafe.product.domain.model.queries.GetAllProductsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {
    Optional<Product> handle(GetProductByIdQuery query);
    List<Product> handle(GetAllProductsQuery query);
    List<Product> handle(GetAllProductsByBranchIdQuery query);
}