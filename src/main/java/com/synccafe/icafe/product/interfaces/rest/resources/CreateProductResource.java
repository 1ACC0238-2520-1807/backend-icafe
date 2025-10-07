package com.synccafe.icafe.product.interfaces.rest.resources;

import com.synccafe.icafe.product.domain.model.valueobjects.ProductType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateProductResource(
        @NotNull(message = "Owner ID is required")
        @Positive(message = "Owner ID must be positive")
        Long ownerId,

        @NotNull(message = "Branch ID is required")
        @Positive(message = "Branch ID must be positive")
        Long branchId,

        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must not exceed 100 characters")
        String name,

        @Size(max = 50, message = "Category must not exceed 50 characters")
        String category,

        @NotNull(message = "Product type is required")
        ProductType type,

        @NotNull(message = "Portions is required")
        @Positive(message = "Portions must be positive")
        Integer portions,

        @Size(max = 1000, message = "Steps must not exceed 1000 characters")
        String steps,

        @Valid
        DirectItemSpecResource directItem,

        @Valid
        List<RecipeItemResource> components
) {
    public record DirectItemSpecResource(
            @NotNull(message = "Item ID is required")
            @Positive(message = "Item ID must be positive")
            Long itemId,

            @NotNull(message = "Portion factor is required")
            @Positive(message = "Portion factor must be positive")
            Double portionFactor
    ) {}

    public record RecipeItemResource(
            @NotNull(message = "Item ID is required")
            @Positive(message = "Item ID must be positive")
            Long itemId,

            @NotNull(message = "Quantity per portion is required")
            @Positive(message = "Quantity per portion must be positive")
            Double qtyPerPortion
    ) {}
}