package com.synccafe.icafe.sales.interfaces.rest;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.domain.model.queries.GetAllSalesQuery;
import com.synccafe.icafe.sales.domain.model.queries.GetSaleByIdQuery;
import com.synccafe.icafe.sales.domain.model.queries.GetSalesByBranchIdQuery;
import com.synccafe.icafe.sales.domain.services.SaleCommandService;
import com.synccafe.icafe.sales.domain.services.SaleQueryService;
import com.synccafe.icafe.sales.interfaces.rest.resources.CreateSaleResource;
import com.synccafe.icafe.sales.interfaces.rest.resources.SaleResource;
import com.synccafe.icafe.sales.interfaces.rest.transform.CreateSaleCommandFromResourceAssembler;
import com.synccafe.icafe.sales.interfaces.rest.transform.SaleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/v1/sales", produces = "application/json")
@Tag(name = "Sales", description = "Sales Management Endpoints")
public class SalesController {

    private final SaleCommandService saleCommandService;
    private final SaleQueryService saleQueryService;

    public SalesController(SaleCommandService saleCommandService, SaleQueryService saleQueryService) {
        this.saleCommandService = saleCommandService;
        this.saleQueryService = saleQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sale created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SaleResource> createSale(@Valid @RequestBody CreateSaleResource resource) {
        var createSaleCommand = CreateSaleCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Sale> sale = saleCommandService.handle(createSaleCommand);
        
        if (sale.isPresent()) {
            var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(sale.get());
            return new ResponseEntity<>(saleResource, HttpStatus.CREATED);
        }
        
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{saleId}")
    @Operation(summary = "Get sale by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sale found"),
        @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    public ResponseEntity<SaleResource> getSaleById(@PathVariable Long saleId) {
        var getSaleByIdQuery = new GetSaleByIdQuery(saleId);
        Optional<Sale> sale = saleQueryService.handle(getSaleByIdQuery);
        
        if (sale.isPresent()) {
            var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(sale.get());
            return ResponseEntity.ok(saleResource);
        }
        
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get all sales")
    @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    public ResponseEntity<List<SaleResource>> getAllSales() {
        var getAllSalesQuery = new GetAllSalesQuery();
        List<Sale> sales = saleQueryService.handle(getAllSalesQuery);
        
        List<SaleResource> saleResources = sales.stream()
            .map(SaleResourceFromEntityAssembler::toResourceFromEntity)
            .toList();
            
        return ResponseEntity.ok(saleResources);
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get sales by branch ID")
    @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    public ResponseEntity<List<SaleResource>> getSalesByBranchId(@PathVariable Long branchId) {
        var getSalesByBranchIdQuery = new GetSalesByBranchIdQuery(branchId);
        List<Sale> sales = saleQueryService.handle(getSalesByBranchIdQuery);
        
        List<SaleResource> saleResources = sales.stream()
            .map(SaleResourceFromEntityAssembler::toResourceFromEntity)
            .toList();
            
        return ResponseEntity.ok(saleResources);
    }

    @PutMapping("/{saleId}/complete")
    @Operation(summary = "Complete a sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sale completed successfully"),
        @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    public ResponseEntity<SaleResource> completeSale(@PathVariable Long saleId) {
        Optional<Sale> sale = saleCommandService.completeSale(saleId);
        
        if (sale.isPresent()) {
            var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(sale.get());
            return ResponseEntity.ok(saleResource);
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{saleId}/cancel")
    @Operation(summary = "Cancel a sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sale cancelled successfully"),
        @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    public ResponseEntity<SaleResource> cancelSale(@PathVariable Long saleId) {
        Optional<Sale> sale = saleCommandService.cancelSale(saleId);
        
        if (sale.isPresent()) {
            var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(sale.get());
            return ResponseEntity.ok(saleResource);
        }
        
        return ResponseEntity.notFound().build();
    }
}