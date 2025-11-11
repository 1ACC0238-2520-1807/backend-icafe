package com.synccafe.icafe.product.interfaces.rest;

import com.synccafe.icafe.product.domain.model.commands.CreateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.DeleteSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.commands.UpdateSupplyItemCommand;
import com.synccafe.icafe.product.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetSupplyItemByIdQuery;
import com.synccafe.icafe.product.domain.services.SupplyItemCommandService;
import com.synccafe.icafe.product.domain.services.SupplyItemQueryService;
import com.synccafe.icafe.product.interfaces.rest.resources.CreateSupplyItemResource;
import com.synccafe.icafe.product.interfaces.rest.resources.SupplyItemResource;
import com.synccafe.icafe.product.interfaces.rest.resources.UpdateSupplyItemResource;
import com.synccafe.icafe.product.interfaces.rest.transform.CreateSupplyItemCommandFromResourceAssembler;
import com.synccafe.icafe.product.interfaces.rest.transform.SupplyItemResourceFromEntityAssembler;
import com.synccafe.icafe.product.interfaces.rest.transform.UpdateSupplyItemCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supply-items")
@Tag(name = "SupplyIem", description = "SupplyItem management operations")
public class SupplyItemController {
    private final SupplyItemCommandService supplyItemCommandService;
    private final SupplyItemQueryService supplyItemQueryService;

    public SupplyItemController(SupplyItemCommandService supplyItemCommandService,
                                SupplyItemQueryService supplyItemQueryService) {
        this.supplyItemCommandService = supplyItemCommandService;
        this.supplyItemQueryService = supplyItemQueryService;
    }

    @PostMapping
    public ResponseEntity<SupplyItemResource> createSupplyItem(@RequestBody CreateSupplyItemResource resource) {
        CreateSupplyItemCommand command = CreateSupplyItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var supplyItem = supplyItemCommandService.handle(command);
        SupplyItemResource supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(supplyItem.get());
        return new ResponseEntity<>(supplyItemResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SupplyItemResource>> getAllSupplyItems() {
        GetAllSupplyItemsQuery query = new GetAllSupplyItemsQuery();
        var supplyItems = supplyItemQueryService.handle(query);
        List<SupplyItemResource> supplyItemResources = supplyItems.stream()
                .map(SupplyItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(supplyItemResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyItemResource> getSupplyItemById(@PathVariable Long id) {
        var supplyItemOptional = supplyItemQueryService.handle(new GetSupplyItemByIdQuery(id));
        if (supplyItemOptional.isPresent()) {
            SupplyItemResource supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(supplyItemOptional.get());
            return ResponseEntity.ok(supplyItemResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplyItemResource> updateSupplyItem(@PathVariable Long id, @RequestBody UpdateSupplyItemResource resource) {
        UpdateSupplyItemCommand command = UpdateSupplyItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var supplyItemOptional = supplyItemCommandService.handle(id,command);
        if (supplyItemOptional.isPresent()) {
            SupplyItemResource supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(supplyItemOptional.get());
            return ResponseEntity.ok(supplyItemResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SupplyItemResource> deleteSupplyItem(@PathVariable Long id) {
        DeleteSupplyItemCommand command = new DeleteSupplyItemCommand(id);
        var supplyItemOptional = supplyItemCommandService.handle(command);
        if (supplyItemOptional.isPresent()) {
            SupplyItemResource supplyItemResource = SupplyItemResourceFromEntityAssembler.toResourceFromEntity(supplyItemOptional.get());
            return ResponseEntity.ok(supplyItemResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{branchId}/branch")
    public ResponseEntity<List<SupplyItemResource>> getAllSupplyItemsByBranchId(@PathVariable Long branchId) {
        var supplyItems = supplyItemQueryService.handle(branchId);
        List<SupplyItemResource> supplyItemResources = supplyItems.stream()
                .map(SupplyItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(supplyItemResources);
    }
}
