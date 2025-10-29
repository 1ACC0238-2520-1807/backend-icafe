package com.synccafe.icafe.product.application.internal.commandservices;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.commands.*;
import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.services.ProductCommandService;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.synccafe.icafe.product.infrastructure.acl.InventoryACLService;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.SupplyItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final SupplyItemRepository supplyItemRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository, SupplyItemRepository supplyItemRepository) {
        this.productRepository = productRepository;
        this.supplyItemRepository = supplyItemRepository;
    }

    @Override
    public Optional<Product> handle(CreateProductCommand command) {
        if(productRepository.existsProductsByName(command.name())) {
            throw new IllegalArgumentException("Product with name " + command.name() + " already exists.");
        }
        var product = new Product(command);
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> handle(Long productId, UpdateProductCommand command) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        var product = productOpt.get();
        product.updateProduct(command);
        productRepository.save(product);
        return Optional.of(product);
    }


    @Override
    public Optional<Product> handle(Long productId,ActivateProductCommand command) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        var product = productOpt.get();
        product.activateProductStatus();
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> handle(Long productId,ArchiveProductCommand command) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        var product = productOpt.get();
        product.archivedProductStatus();
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> handle(AddIngredientCommand command) {
        var productOpt = productRepository.findById(command.productId());
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + command.productId() + " does not exist.");
        }
        var product = productOpt.get();
        SupplyItem supplyItem = supplyItemRepository.findById(command.supplyItemId())
                .orElseThrow(() -> new IllegalArgumentException("Supply item not found"));
        product.addIngredient(supplyItem, command.quantity());
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public void handle(RemoveIngredientCommand command) {
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.removeIngredient(command);
        productRepository.save(product);
    }
}