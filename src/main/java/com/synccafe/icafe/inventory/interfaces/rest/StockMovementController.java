package com.synccafe.icafe.inventory.interfaces.rest;

import com.synccafe.icafe.inventory.domain.model.queries.GetAllInventoryTransactionsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetInventoryTransactionByIdQuery;
import com.synccafe.icafe.inventory.domain.service.InventoryTransactionCommandService;
import com.synccafe.icafe.inventory.domain.service.InventoryTransactionQueryService;
import com.synccafe.icafe.inventory.interfaces.rest.resources.CreateInventoryTransactionResource;
import com.synccafe.icafe.inventory.interfaces.rest.resources.InventoryTransactionResource;
import com.synccafe.icafe.inventory.interfaces.rest.transform.InventoryTransactionResourceFromEntityAssembler;
import com.synccafe.icafe.inventory.interfaces.rest.transform.RegisterInventoryTransactionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/inventory/movements", produces = "application/json")
@Tag(name = "Stock Movements", description = "Stock Movement Management Endpoints")
public class StockMovementController {
    
    private final InventoryTransactionCommandService inventoryTransactionCommandService;
    private final InventoryTransactionQueryService inventoryTransactionQueryService;

    public StockMovementController(InventoryTransactionCommandService inventoryTransactionCommandService,
                                  InventoryTransactionQueryService inventoryTransactionQueryService) {
        this.inventoryTransactionCommandService = inventoryTransactionCommandService;
        this.inventoryTransactionQueryService = inventoryTransactionQueryService;
    }

    @PostMapping
    public ResponseEntity<InventoryTransactionResource> registerMovement(@RequestBody CreateInventoryTransactionResource resource) {
        var registerInventoryTransactionCommand = RegisterInventoryTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        var inventoryTransaction = inventoryTransactionCommandService.handle(registerInventoryTransactionCommand);
        
        if (inventoryTransaction.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        var inventoryTransactionResource = InventoryTransactionResourceFromEntityAssembler.toResourceFromEntity(inventoryTransaction.get());
        return new ResponseEntity<>(inventoryTransactionResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryTransactionResource>> getAllMovements() {
        var getAllInventoryTransactionsQuery = new GetAllInventoryTransactionsQuery();
        var inventoryTransactions = inventoryTransactionQueryService.handle(getAllInventoryTransactionsQuery);
        
        var inventoryTransactionResources = inventoryTransactions.stream()
                .map(InventoryTransactionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(inventoryTransactionResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryTransactionResource> getMovementById(@PathVariable Long id) {
        var getInventoryTransactionByIdQuery = new GetInventoryTransactionByIdQuery(id);
        var inventoryTransaction = inventoryTransactionQueryService.handle(getInventoryTransactionByIdQuery);
        
        if (inventoryTransaction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var inventoryTransactionResource = InventoryTransactionResourceFromEntityAssembler.toResourceFromEntity(inventoryTransaction.get());
        return ResponseEntity.ok(inventoryTransactionResource);
    }
}