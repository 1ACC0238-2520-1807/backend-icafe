package com.synccafe.icafe.product.interfaces.rest;

import com.synccafe.icafe.product.domain.model.aggregates.Product;
import com.synccafe.icafe.product.domain.model.commands.*;
import com.synccafe.icafe.product.domain.model.queries.GetAllProductsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetProductByIdQuery;
import com.synccafe.icafe.product.domain.services.ProductCommandService;
import com.synccafe.icafe.product.domain.services.ProductQueryService;
import com.synccafe.icafe.product.interfaces.rest.resources.AddIngredientResource;
import com.synccafe.icafe.product.interfaces.rest.resources.CreateProductResource;
import com.synccafe.icafe.product.interfaces.rest.resources.ProductResource;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateProductResource;
import com.synccafe.icafe.product.interfaces.rest.transform.AddIngredientCommandFromResourceAssembler;
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
    public ResponseEntity<ProductResource> createProduct(@RequestBody CreateProductResource  resource){
        CreateProductCommand command = CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var product = productCommandService.handle(command);
        ProductResource productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
        return new ResponseEntity<>(productResource, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResource> getProductById(
            @Parameter(description = "ID of the product to retrieve") @PathVariable Long productId) {
        GetProductByIdQuery query = new GetProductByIdQuery(productId);
        Optional<Product> productOpt = productQueryService.handle(query);
        if (productOpt.isPresent()) {
            ProductResource productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(productOpt.get());
            return ResponseEntity.ok(productResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResource> updateProduct(
            @Parameter(description = "ID of the product to update") @PathVariable Long productId,
            @Valid @RequestBody UpdateProductResource resource) {
        UpdateProductCommand command = UpdateProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var updatedProductOpt = productCommandService.handle(productId, command);
        if (updatedProductOpt.isPresent()) {
            ProductResource productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(updatedProductOpt.get());
            return ResponseEntity.ok(productResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Agregar ingredientes al producto
    @PostMapping("/{productId}/ingredients")
    public ResponseEntity<ProductResource> addIngredientsToProduct(
            @Parameter(description = "ID of the product to add ingredients to") @PathVariable Long productId,
            @RequestBody AddIngredientResource resource) {
        AddIngredientCommand command = AddIngredientCommandFromResourceAssembler.toCommandFromResource(productId, resource);
        var updatedProductOpt = productCommandService.handle(command);
        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(updatedProductOpt.get());
        return ResponseEntity.ok(productResource);
    }

}