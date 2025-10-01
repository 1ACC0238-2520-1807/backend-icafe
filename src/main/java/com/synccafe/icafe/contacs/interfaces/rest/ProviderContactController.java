package com.synccafe.icafe.contacs.interfaces.rest;

import com.synccafe.icafe.contacs.domain.model.queries.GetAllProviderContactQuery;
import com.synccafe.icafe.contacs.domain.service.ProviderContactCommandService;
import com.synccafe.icafe.contacs.domain.service.ProviderContactQueryService;
import com.synccafe.icafe.contacs.interfaces.rest.resources.CreateProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.resources.ProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.transform.CreateProviderContactCommandFromResourceAssembler;
import com.synccafe.icafe.contacs.interfaces.rest.transform.ProviderContactResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/providercontacts", produces = "application/json")
@Tag(name = "Provider-Contact", description = "Provider Contact Management Endpoints")
public class ProviderContactController {
    private final ProviderContactCommandService providerContactCommandService;
    private final ProviderContactQueryService providerContactQueryService;

    public ProviderContactController(ProviderContactCommandService providerContactCommandService, ProviderContactQueryService providerContactQueryService) {
        this.providerContactCommandService = providerContactCommandService;
        this.providerContactQueryService = providerContactQueryService;
    }

    @PostMapping
    public ResponseEntity<ProviderContactResource> createProviderContact(@RequestBody CreateProviderContactResource resource) {
        var createProviderContactCommand= CreateProviderContactCommandFromResourceAssembler.toCommandFromResource(resource);
        var providerContact =providerContactCommandService.handle(createProviderContactCommand);
        if(providerContact.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var providerContactResource = ProviderContactResourceFromEntityAssembler.toResourceFromEntity(providerContact.get());// Implementation for creating a provider contact
        return new ResponseEntity<>(providerContactResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProviderContactResource>> getAllProviderContacts() {
        var getAllProviderContactQuery = new GetAllProviderContactQuery();
        var providerContacts = providerContactQueryService.handle(getAllProviderContactQuery);
        var providerContactResources = providerContacts.stream()
                .map(ProviderContactResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(providerContactResources);
    }
}
