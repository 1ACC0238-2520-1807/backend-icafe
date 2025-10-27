package com.synccafe.icafe.shared.interfaces.rest;


import com.synccafe.icafe.shared.domain.model.commands.DeleteBranchCommand;
import com.synccafe.icafe.shared.domain.model.commands.UpdateBranchCommand;
import com.synccafe.icafe.shared.domain.model.queries.GetAllBranchByOwnerIdQuery;
import com.synccafe.icafe.shared.domain.model.queries.GetBranchByIdQuery;
import com.synccafe.icafe.shared.domain.services.BranchCommandService;
import com.synccafe.icafe.shared.domain.services.BranchQueryService;
import com.synccafe.icafe.shared.interfaces.rest.resources.BranchResource;
import com.synccafe.icafe.shared.interfaces.rest.resources.CreateBranchResource;
import com.synccafe.icafe.shared.interfaces.rest.resources.UpdateBranchResource;
import com.synccafe.icafe.shared.interfaces.rest.transform.BranchResourceFromEntityAssembler;
import com.synccafe.icafe.shared.interfaces.rest.transform.CreateBranchCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/branch", produces = "application/json")
@Tag(name = "Branch", description = "Branch Management Endpoints")
public class BranchController {
    private final BranchCommandService branchCommandService;
    private final BranchQueryService branchQueryService;

    public BranchController(BranchCommandService branchCommandService, BranchQueryService branchQueryService) {
        this.branchCommandService = branchCommandService;
        this.branchQueryService = branchQueryService;
    }

    @PostMapping
    public ResponseEntity<BranchResource> createBranch(@RequestBody CreateBranchResource resource) {
        var createBranchCommand= CreateBranchCommandFromResourceAssembler.toCommandFromResource(resource);
        var branch =branchCommandService.handle(createBranchCommand);
        if(branch.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var branchResource = BranchResourceFromEntityAssembler.toResourceFromEntity(branch.get());
        return new ResponseEntity<>(branchResource, HttpStatus.CREATED);
    }

    @GetMapping("/owner/{ownerId}")
    public List<BranchResource> getAllBranchesByOwner(@PathVariable Long ownerId) {
        var getAllBranchByOwnerIdQuery = new GetAllBranchByOwnerIdQuery(ownerId);
        var branch =branchQueryService.handle(getAllBranchByOwnerIdQuery);
        if(branch.isEmpty()) return List.of();
        var branchResources = branch.stream()
                .map(BranchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return branchResources;
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchResource> getBranchById(@PathVariable Long branchId) {
        var getBranchByIdQuery = new GetBranchByIdQuery(branchId);
        var branch = branchQueryService.handle(getBranchByIdQuery);
        if (branch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var branchResource = BranchResourceFromEntityAssembler.toResourceFromEntity(branch.get());
        return ResponseEntity.ok(branchResource);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<BranchResource> updateBranch(@PathVariable Long branchId, @RequestBody UpdateBranchResource resource) {
        var updateBranchCommand = new UpdateBranchCommand(resource.name(), resource.address());
        var branch = branchCommandService.handle(branchId, updateBranchCommand);
        if (branch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var branchResource = BranchResourceFromEntityAssembler.toResourceFromEntity(branch.get());
        return ResponseEntity.ok(branchResource);
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<BranchResource> deleteBranch(@PathVariable Long branchId) {
        var deleteBranchCommand = new DeleteBranchCommand(branchId);
        var branch = branchCommandService.handle(deleteBranchCommand);
        if (branch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var branchResource = BranchResourceFromEntityAssembler.toResourceFromEntity(branch.get());
        return ResponseEntity.ok(branchResource);
    }


}
