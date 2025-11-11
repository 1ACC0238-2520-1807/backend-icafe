package com.synccafe.icafe.purchasing.interfaces.rest;

import com.synccafe.icafe.purchasing.domain.model.queries.GetPurchaseOrderByIdQuery;
import com.synccafe.icafe.purchasing.domain.model.queries.GetPurchaseOrdersByBranchQuery;
import com.synccafe.icafe.purchasing.domain.services.PurchaseOrderCommandService;
import com.synccafe.icafe.purchasing.domain.services.PurchaseOrderQueryService;
import com.synccafe.icafe.purchasing.interfaces.rest.resources.CreatePurchaseOrderResource;
import com.synccafe.icafe.purchasing.interfaces.rest.resources.PurchaseOrderResource;
import com.synccafe.icafe.purchasing.interfaces.rest.transform.CreatePurchaseOrderCommandFromResourceAssembler;
import com.synccafe.icafe.purchasing.interfaces.rest.transform.PurchaseOrderResourceFromEntityAssembler;
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

@RestController
@RequestMapping("/api/v1/purchase-orders")
@Tag(name = "Purchase Orders", description = "Purchase order management operations")
public class PurchaseOrderController {

    private final PurchaseOrderCommandService purchaseOrderCommandService;
    private final PurchaseOrderQueryService purchaseOrderQueryService;
    private final PurchaseOrderResourceFromEntityAssembler purchaseOrderResourceFromEntityAssembler;

    public PurchaseOrderController(PurchaseOrderCommandService purchaseOrderCommandService,
                                 PurchaseOrderQueryService purchaseOrderQueryService,
                                 PurchaseOrderResourceFromEntityAssembler purchaseOrderResourceFromEntityAssembler) {
        this.purchaseOrderCommandService = purchaseOrderCommandService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
        this.purchaseOrderResourceFromEntityAssembler = purchaseOrderResourceFromEntityAssembler;
    }

    @PostMapping
    @Operation(summary = "Create purchase order", description = "Create a new purchase order for supply items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Purchase order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<PurchaseOrderResource> createPurchaseOrder(@Valid @RequestBody CreatePurchaseOrderResource resource) {
        try {
            var command = CreatePurchaseOrderCommandFromResourceAssembler.toCommandFromResource(resource);
            var purchaseOrder = purchaseOrderCommandService.handle(command);
            
            if (purchaseOrder.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var purchaseOrderResource = purchaseOrderResourceFromEntityAssembler.toResourceFromEntity(purchaseOrder.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrderResource);
        }
        catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get purchase orders by branch", description = "Get all purchase orders for a specific branch")
    public ResponseEntity<List<PurchaseOrderResource>> getPurchaseOrdersByBranch(
            @Parameter(description = "ID of the branch") @PathVariable Long branchId) {
        var query = new GetPurchaseOrdersByBranchQuery(branchId);
        var purchaseOrders = purchaseOrderQueryService.handle(query);
        
        var resources = purchaseOrders.stream()
                .map(purchaseOrderResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{purchaseOrderId}/branch/{branchId}")
    @Operation(summary = "Get purchase order by ID", description = "Get a specific purchase order by ID and branch")
    public ResponseEntity<PurchaseOrderResource> getPurchaseOrderById(
            @Parameter(description = "ID of the purchase order") @PathVariable Long purchaseOrderId,
            @Parameter(description = "ID of the branch") @PathVariable Long branchId) {
        var query = new GetPurchaseOrderByIdQuery(purchaseOrderId, branchId);
        var purchaseOrder = purchaseOrderQueryService.handle(query);
        
        if (purchaseOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var resource = purchaseOrderResourceFromEntityAssembler.toResourceFromEntity(purchaseOrder.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{purchaseOrderId}/branch/{branchId}/confirm")
    @Operation(summary = "Confirm purchase order", description = "Confirm a pending purchase order")
    public ResponseEntity<PurchaseOrderResource> confirmPurchaseOrder(
            @Parameter(description = "ID of the purchase order") @PathVariable Long purchaseOrderId,
            @Parameter(description = "ID of the branch") @PathVariable Long branchId) {
        var purchaseOrder = purchaseOrderCommandService.confirmPurchaseOrder(purchaseOrderId, branchId);
        
        if (purchaseOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var resource = purchaseOrderResourceFromEntityAssembler.toResourceFromEntity(purchaseOrder.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{purchaseOrderId}/branch/{branchId}/complete")
    @Operation(summary = "Complete purchase order", description = "Mark a confirmed purchase order as completed")
    public ResponseEntity<PurchaseOrderResource> completePurchaseOrder(
            @Parameter(description = "ID of the purchase order") @PathVariable Long purchaseOrderId,
            @Parameter(description = "ID of the branch") @PathVariable Long branchId) {
        var purchaseOrder = purchaseOrderCommandService.completePurchaseOrder(purchaseOrderId, branchId);
        
        if (purchaseOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var resource = purchaseOrderResourceFromEntityAssembler.toResourceFromEntity(purchaseOrder.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{purchaseOrderId}/branch/{branchId}/cancel")
    @Operation(summary = "Cancel purchase order", description = "Cancel a purchase order")
    public ResponseEntity<PurchaseOrderResource> cancelPurchaseOrder(
            @Parameter(description = "ID of the purchase order") @PathVariable Long purchaseOrderId,
            @Parameter(description = "ID of the branch") @PathVariable Long branchId) {
        var purchaseOrder = purchaseOrderCommandService.cancelPurchaseOrder(purchaseOrderId, branchId);
        
        if (purchaseOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var resource = purchaseOrderResourceFromEntityAssembler.toResourceFromEntity(purchaseOrder.get());
        return ResponseEntity.ok(resource);
    }
}