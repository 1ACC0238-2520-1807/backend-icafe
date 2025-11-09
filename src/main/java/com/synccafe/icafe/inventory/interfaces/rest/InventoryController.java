package com.synccafe.icafe.inventory.interfaces.rest;

import com.synccafe.icafe.inventory.domain.model.queries.GetCurrentStockQuery;
import com.synccafe.icafe.inventory.domain.services.InventoryCommandService;
import com.synccafe.icafe.inventory.domain.services.InventoryQueryService;
import com.synccafe.icafe.inventory.interfaces.rest.resources.CurrentStockResource;
import com.synccafe.icafe.inventory.interfaces.rest.resources.RegisterStockMovementResource;
import com.synccafe.icafe.inventory.interfaces.rest.transform.CurrentStockResourceFromEntityAssembler;
import com.synccafe.icafe.inventory.interfaces.rest.transform.RegisterStockMovementCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/inventory", produces = "application/json")
@Tag(name = "Inventory", description = "Inventory Management Endpoints")
public class InventoryController {
    
    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;
    
    public InventoryController(InventoryCommandService inventoryCommandService,
                              InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }
    
    @PostMapping("/movements")
    @Operation(summary = "Register a stock movement", description = "Register an entry or exit movement for a supply item by branch")
    public ResponseEntity<Void> registerStockMovement(@RequestBody RegisterStockMovementResource resource) {
        var command = RegisterStockMovementCommandFromResourceAssembler.toCommandFromResource(resource);
        inventoryCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/stock/{branchId}/{supplyItemId}")
    @Operation(summary = "Get current stock", description = "Get the current stock level for a specific supply item in a branch")
    public ResponseEntity<CurrentStockResource> getCurrentStock(@PathVariable Long branchId, @PathVariable Long supplyItemId) {
        var query = new GetCurrentStockQuery(branchId, supplyItemId);
        var currentStock = inventoryQueryService.handle(query);
        var resource = CurrentStockResourceFromEntityAssembler.toResourceFromStock(branchId, supplyItemId, currentStock);
        return ResponseEntity.ok(resource);
    }
}