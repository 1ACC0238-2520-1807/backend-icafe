package com.synccafe.icafe.inventory.interfaces.rest;

import com.synccafe.icafe.inventory.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetSupplyItemByIdQuery;
import com.synccafe.icafe.inventory.domain.service.SupplyItemCommandService;
import com.synccafe.icafe.inventory.domain.service.SupplyItemQueryService;
import com.synccafe.icafe.inventory.interfaces.rest.resources.CreateSupplyItemResource;
import com.synccafe.icafe.inventory.interfaces.rest.resources.SupplyItemResource;
import com.synccafe.icafe.inventory.interfaces.rest.resources.UpdateSupplyItemResource;
import com.synccafe.icafe.inventory.interfaces.rest.transform.RegisterSupplyItemCommandFromResourceAssembler;
import com.synccafe.icafe.inventory.interfaces.rest.transform.SupplyItemResourceFromEntityAssembler;
import com.synccafe.icafe.inventory.interfaces.rest.transform.UpdateSupplyItemCommandFromResourceAssembler;
import com.synccafe.icafe.inventory.domain.model.commands.RemoveSupplyItemCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/inventory", produces = "application/json")
@Tag(name = "Inventory", description = "Inventory Management Endpoints")
public class InventoryController {
    
    private final SupplyItemCommandService supplyItemCommandService;
    private final SupplyItemQueryService supplyItemQueryService;

    public InventoryController(SupplyItemCommandService supplyItemCommandService,
                              SupplyItemQueryService supplyItemQueryService) {
        this.supplyItemCommandService = supplyItemCommandService;
        this.supplyItemQueryService = supplyItemQueryService;
    }

    @PostMapping("/items")
    public ResponseEntity<SupplyItemResource> createSupplyItem(@RequestBody CreateSupplyItemResource resource) {
        var registerSupplyItemCommand = RegisterSupplyItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var supplyItem = supplyItemCommandService.handle(registerSupplyItemCommand);
        
        if (supplyItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        var supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(supplyItem.get());
        return new ResponseEntity<>(supplyItemResource, HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<List<SupplyItemResource>> getAllSupplyItems() {
        var getAllSupplyItemsQuery = new GetAllSupplyItemsQuery();
        var supplyItems = supplyItemQueryService.handle(getAllSupplyItemsQuery);
        
        var supplyItemResources = supplyItems.stream()
                .map(SupplyItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(supplyItemResources);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<SupplyItemResource> getSupplyItemById(@PathVariable Long id) {
        var getSupplyItemByIdQuery = new GetSupplyItemByIdQuery(id);
        var supplyItem = supplyItemQueryService.handle(getSupplyItemByIdQuery);
        
        if (supplyItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(supplyItem.get());
        return ResponseEntity.ok(supplyItemResource);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<SupplyItemResource> updateSupplyItem(@PathVariable Long id,
                                                              @RequestBody UpdateSupplyItemResource resource) {
        var updateSupplyItemCommand = UpdateSupplyItemCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedSupplyItem = supplyItemCommandService.handle(updateSupplyItemCommand);
        
        if (updatedSupplyItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(updatedSupplyItem.get());
        return ResponseEntity.ok(supplyItemResource);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteSupplyItem(@PathVariable Long id) {
        var removeSupplyItemCommand = new RemoveSupplyItemCommand(id);
        
        try {
            supplyItemCommandService.handle(removeSupplyItemCommand);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}