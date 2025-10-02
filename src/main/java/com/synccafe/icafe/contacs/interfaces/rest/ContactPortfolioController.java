package com.synccafe.icafe.contacs.interfaces.rest;

import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.interfaces.rest.resources.CreateEmployeeContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.resources.CreateProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.resources.EmployeeContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.resources.ProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.transform.CreateEmployeeContactCommandFromResourceAssembler;
import com.synccafe.icafe.contacs.interfaces.rest.resources.UpdateProviderContactResource;
import com.synccafe.icafe.contacs.interfaces.rest.transform.CreateProviderContactCommandFromResourceAssembler;
import com.synccafe.icafe.contacs.interfaces.rest.transform.EmployeeContactResourceFromEntityAssembler;
import com.synccafe.icafe.contacs.interfaces.rest.transform.ProviderContactResourceFromEntityAssembler;
import com.synccafe.icafe.contacs.interfaces.rest.transform.UpdateProviderContactCommandFromResourceAssembler;
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

    //Agregar un empleado
    @PostMapping("/{portfolioId}/employees")
    public ResponseEntity<EmployeeContactResource> addEmployeeToPortfolio(@PathVariable Long portfolioId,
                                                                          @RequestBody CreateEmployeeContactResource resource) {
        var createEmployeeContactCommand= CreateEmployeeContactCommandFromResourceAssembler.toCommandFromResource(resource);
        var employeeContact = portfolioCommandService.addEmployeeToPortfolio(portfolioId, createEmployeeContactCommand);
        if(employeeContact==null) {
            return ResponseEntity.badRequest().build();
        }
        var employeeContactResource = EmployeeContactResourceFromEntityAssembler.toResourceFromEntity(employeeContact);// Implementation for creating an employee contact
        return new ResponseEntity<>(employeeContactResource, HttpStatus.CREATED);
    }

    // Actualizar datos de un proveedor
    @PutMapping("/{portfolioId}/providers/{providerId}")
    public ResponseEntity<ProviderContactResource> updateProviderInPortfolio(
            @PathVariable Long portfolioId,
            @PathVariable Long providerId,
            @RequestBody UpdateProviderContactResource resource) {

        var updateProviderContactCommand = UpdateProviderContactCommandFromResourceAssembler.toCommandFromResource(resource);
        var updatedProvider = portfolioCommandService.updateProviderInPortfolio(portfolioId, providerId, updateProviderContactCommand);

        if (updatedProvider == null) {
            return ResponseEntity.notFound().build();
        }

        var providerResource = ProviderContactResourceFromEntityAssembler.toResourceFromEntity(updatedProvider);
        return ResponseEntity.ok(providerResource);
    }

}
