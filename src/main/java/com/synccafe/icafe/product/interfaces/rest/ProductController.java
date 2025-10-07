package com.synccafe.icafe.product.interfaces.rest;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.commands.*;
import com.synccafe.icafe.product.domain.model.queries.GetAllProductsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetProductByIdQuery;
import com.synccafe.icafe.product.domain.services.ProductCommandService;
import com.synccafe.icafe.product.domain.services.ProductQueryService;
import com.synccafe.icafe.product.interfaces.rest.resources.CreateProductResource;
import com.synccafe.icafe.product.interfaces.rest.resources.ProductResource;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateProductResource;
import com.synccafe.icafe.product.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.synccafe.icafe.product.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.synccafe.icafe.product.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product management operations")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductController(ProductCommandService productCommandService, 
                           ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Product with same name already exists")
    })
    public ResponseEntity<ProductResource> createProduct(
            @Valid @RequestBody CreateProductResource resource) {
        
        CreateProductCommand command = CreateProductCommandFromResourceAssembler
                .toCommandFromResource(resource);
        
        Long productId = productCommandService.handle(command);
        
        if (productId != null && productId > 0) {
            GetProductByIdQuery query = new GetProductByIdQuery(productId);
            Optional<Product> product = productQueryService.handle(query);
            
            if (product.isPresent()) {
                ProductResource productResource = ProductResourceFromEntityAssembler
                        .toResourceFromEntity(product.get());
                return new ResponseEntity<>(productResource, HttpStatus.CREATED);
            }
        }
        
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResource> getProductById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId) {
        
        GetProductByIdQuery query = new GetProductByIdQuery(productId);
        Optional<Product> product = productQueryService.handle(query);
        
        if (product.isPresent()) {
            ProductResource productResource = ProductResourceFromEntityAssembler
                    .toResourceFromEntity(product.get());
            return ResponseEntity.ok(productResource);
        }
        
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products for a specific owner and branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductResource>> getAllProducts(
            @Parameter(description = "Owner ID", required = true)
            @RequestParam Long ownerId,
            @Parameter(description = "Branch ID", required = true)
            @RequestParam Long branchId) {
        
        GetAllProductsQuery query = new GetAllProductsQuery(ownerId, branchId);
        List<Product> products = productQueryService.handle(query);
        
        List<ProductResource> productResources = ProductResourceFromEntityAssembler
                .toResourceListFromEntityList(products);
        
        return ResponseEntity.ok(productResources);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update product", description = "Updates an existing product with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProductResource> updateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductResource resource) {
        
        try {
            UpdateProductCommand command = UpdateProductCommandFromResourceAssembler
                    .toCommandFromResource(productId, resource);
            
            productCommandService.handle(command);
            
            // Query the updated product
            GetProductByIdQuery query = new GetProductByIdQuery(productId);
            Optional<Product> updatedProduct = productQueryService.handle(query);
            
            if (updatedProduct.isPresent()) {
                ProductResource productResource = ProductResourceFromEntityAssembler
                        .toResourceFromEntity(updatedProduct.get());
                return ResponseEntity.ok(productResource);
            }
            
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{productId}/archive")
    @Operation(summary = "Archive product", description = "Archives a product, making it inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product archived successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResource> archiveProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId) {
        
        try {
            ArchiveProductCommand command = new ArchiveProductCommand(productId);
            productCommandService.handle(command);
            
            // Query the archived product
            GetProductByIdQuery query = new GetProductByIdQuery(productId);
            Optional<Product> archivedProduct = productQueryService.handle(query);
            
            if (archivedProduct.isPresent()) {
                ProductResource productResource = ProductResourceFromEntityAssembler
                        .toResourceFromEntity(archivedProduct.get());
                return ResponseEntity.ok(productResource);
            }
            
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{productId}/activate")
    @Operation(summary = "Activate product", description = "Activates an archived product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product activated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResource> activateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId) {
        
        try {
            ActivateProductCommand command = new ActivateProductCommand(productId);
            productCommandService.handle(command);
            
            // Query the activated product
            GetProductByIdQuery query = new GetProductByIdQuery(productId);
            Optional<Product> activatedProduct = productQueryService.handle(query);
            
            if (activatedProduct.isPresent()) {
                ProductResource productResource = ProductResourceFromEntityAssembler
                        .toResourceFromEntity(activatedProduct.get());
                return ResponseEntity.ok(productResource);
            }
            
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product", description = "Permanently deletes a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId) {
        
        try {
            DeleteProductCommand command = new DeleteProductCommand(productId);
            productCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}