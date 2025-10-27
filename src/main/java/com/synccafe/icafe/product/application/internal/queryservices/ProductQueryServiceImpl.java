package com.synccafe.icafe.product.application.internal.queryservices;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.queries.GetAllProductsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetProductByIdQuery;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.services.ProductQueryService;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.productId());
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }
}