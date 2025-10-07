package com.synccafe.icafe.product.application.internal.commandservices;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.commands.*;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.model.valueobjects.OwnerId;
import com.synccafe.icafe.product.domain.services.ProductCommandService;
import com.synccafe.icafe.product.domain.services.ProductPolicy;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.synccafe.icafe.product.infrastructure.acl.InventoryACLService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final ProductPolicy productPolicy;
    private final InventoryACLService inventoryACLService;

    public ProductCommandServiceImpl(ProductRepository productRepository,
                                   ProductPolicy productPolicy,
                                   InventoryACLService inventoryACLService) {
        this.productRepository = productRepository;
        this.productPolicy = productPolicy;
        this.inventoryACLService = inventoryACLService;
    }

    @Override
    @Transactional
    public Long handle(CreateProductCommand command) {
        var ownerId = new OwnerId(command.ownerId());
        var branchId = new BranchId(command.branchId());
        
        var product = new Product(
            ownerId,
            branchId,
            command.name(),
            command.category(),
            command.type(),
            command.portions(),
            command.steps()
        );

        // Set direct item or components based on product type
        switch (command.type()) {
            case SIMPLE -> {
                if (command.directItem() != null) {
                    product.setDirectItem(command.directItem());
                }
            }
            case COMPOSED -> {
                if (command.components() != null && !command.components().isEmpty()) {
                    product.setComponents(command.components());
                }
            }
        }

        // Validate business rules
        productPolicy.validateProductCreation(product, inventoryACLService, 
            new ProductPolicyRepositoryAdapter(productRepository));

        return productRepository.save(product).getId();
    }

    @Override
    @Transactional
    public void handle(UpdateProductCommand command) {
        var product = productRepository.findById(command.productId())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Update basic info if provided
        if (command.name() != null || command.category() != null || command.steps() != null) {
            product.updateBasicInfo(command.name(), command.category(), command.steps());
        }

        // Update portions if provided
        if (command.portions() != null) {
            product.updatePortions(command.portions());
        }

        // Update direct item or components based on product type
        if (command.directItem() != null) {
            product.setDirectItem(command.directItem());
        }

        if (command.components() != null) {
            product.setComponents(command.components());
        }

        // Validate business rules
        productPolicy.validateProductUpdate(product, inventoryACLService,
            new ProductPolicyRepositoryAdapter(productRepository));

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void handle(ArchiveProductCommand command) {
        var product = productRepository.findById(command.productId())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.archive();
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void handle(ActivateProductCommand command) {
        var product = productRepository.findById(command.productId())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.activate();
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void handle(DeleteProductCommand command) {
        var product = productRepository.findById(command.productId())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.delete();
        productRepository.delete(product);
    }

    // Adapter to bridge ProductPolicy interface with actual repository
    private static class ProductPolicyRepositoryAdapter implements ProductPolicy.ProductRepository {
        private final ProductRepository repository;

        public ProductPolicyRepositoryAdapter(ProductRepository repository) {
            this.repository = repository;
        }

        @Override
        public boolean existsByNameAndOwnerIdAndBranchIdAndIdNot(String name, OwnerId ownerId, 
                                                               BranchId branchId, Long excludeId) {
            if (excludeId == null) {
                return repository.existsByNameIgnoreCaseAndOwnerIdAndBranchId(name, ownerId, branchId);
            }
            return repository.existsByNameIgnoreCaseAndOwnerIdAndBranchIdAndIdNot(name, ownerId, branchId, excludeId);
        }
    }
}