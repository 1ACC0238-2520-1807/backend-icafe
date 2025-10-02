package com.synccafe.icafe.contacs.interfaces.rest;

import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.interfaces.rest.resources.CreateProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.resources.ProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.transform.CreateProviderContactCommandFromResourceAssembler;
import com.synccafe.icafe.contacs.interfaces.rest.transform.ProviderContactResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/contact-portfolios", produces = "application/json")
@Tag(name = "Contact-Portfolio", description = "Contact Portfolio Management Endpoints")
public class ContactPortfolioController {
    private final PortfolioCommandService portfolioCommandService;

    public ContactPortfolioController(PortfolioCommandService portfolioCommandService) {
        this.portfolioCommandService = portfolioCommandService;
    }

    //Agregar un provider
    @PostMapping("/{portfolioId}/providers")
    public ResponseEntity<ProviderContactResource> addProviderToPortfolio(@PathVariable Long portfolioId,
                                                                          @RequestBody CreateProviderContactResource resource) {
        var createProviderContactCommand= CreateProviderContactCommandFromResourceAssembler.toCommandFromResource(resource);
        var providerContact = portfolioCommandService.addProviderToPortfolio(portfolioId, createProviderContactCommand);
        if(providerContact==null){
            return ResponseEntity.badRequest().build();
        }
        var providerContactResource = ProviderContactResourceFromEntityAssembler.toResourceFromEntity(providerContact);// Implementation for creating a provider contact
        return new ResponseEntity<>(providerContactResource, HttpStatus.CREATED);
    }
}
