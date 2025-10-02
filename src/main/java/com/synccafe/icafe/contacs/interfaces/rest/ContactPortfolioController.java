package com.synccafe.icafe.contacs.interfaces.rest;

import com.synccafe.icafe.contacs.domain.model.queries.GetPortfolioByIdQuery;
import com.synccafe.icafe.contacs.domain.model.queries.GetProviderContactByIdQuery;
import com.synccafe.icafe.contacs.domain.service.EmployeeContactQueryService;
import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.domain.service.PortfolioQueryService;
import com.synccafe.icafe.contacs.domain.service.ProviderContactQueryService;
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

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/contact-portfolios", produces = "application/json")
@Tag(name = "Contact-Portfolio", description = "Contact Portfolio Management Endpoints")
public class ContactPortfolioController {
    private final PortfolioCommandService portfolioCommandService;
    private final PortfolioQueryService portfolioQueryService;
    private final ProviderContactQueryService providerContactQueryService;
    private final EmployeeContactQueryService employeeContactQueryService;

    public ContactPortfolioController(PortfolioCommandService portfolioCommandService,
                                      PortfolioQueryService portfolioQueryService,
                                      ProviderContactQueryService providerContactQueryService,
                                      EmployeeContactQueryService employeeContactQueryService) {
        this.portfolioCommandService = portfolioCommandService;
        this.portfolioQueryService = portfolioQueryService;
        this.providerContactQueryService = providerContactQueryService;
        this.employeeContactQueryService = employeeContactQueryService;
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
    // Eliminar un proveedor
    @DeleteMapping("/{portfolioId}/providers/{providerId}")
    public ResponseEntity<Void> deleteProviderFromPortfolio(
            @PathVariable Long portfolioId,
            @PathVariable Long providerId) {

        boolean deleted = portfolioCommandService.deleteProviderFromPortfolio(portfolioId, providerId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    //Actualizar un empleado
    @PutMapping("/{portfolioId}/employees/{employeeId}")
    public ResponseEntity<EmployeeContactResource> updateEmployeeInPortfolio(
            @PathVariable Long portfolioId,
            @PathVariable Long employeeId,
            @RequestBody CreateEmployeeContactResource resource) {
        var createEmployeeContactCommand = CreateEmployeeContactCommandFromResourceAssembler.toCommandFromResource(resource);
        var updatedEmployee = portfolioCommandService.updateEmployeeInPortfolio(portfolioId, employeeId,
                createEmployeeContactCommand);
        if (updatedEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        var employeeResource = EmployeeContactResourceFromEntityAssembler.toResourceFromEntity(updatedEmployee);
        return ResponseEntity.ok(employeeResource);
        }

    //Eliminar un empleado
    @DeleteMapping("/{portfolioId}/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeFromPortfolio(
            @PathVariable Long portfolioId,
            @PathVariable Long employeeId) {
        boolean deleted = portfolioCommandService.deleteEmployeeFromPortfolio(portfolioId, employeeId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Obtener todos los proveedores de un portfolio
    @GetMapping("/{portfolioId}/providers")
    public ResponseEntity<List<ProviderContactResource>> getProvidersByPortfolio(@PathVariable Long portfolioId) {
        var getPortfolioByIdQuery = new GetPortfolioByIdQuery(portfolioId);
        var portfolio = portfolioQueryService.handle(getPortfolioByIdQuery);
        if (portfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var providers = portfolio.get().getProviders()
                .stream()
                .map(ProviderContactResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(providers);
    }

    // Obtener un proveedor específico dentro del portfolio
    @GetMapping("/{portfolioId}/providers/{providerId}")
    public ResponseEntity<ProviderContactResource> getProviderById(@PathVariable Long portfolioId,
                                                                   @PathVariable Long providerId) {
        var provider = providerContactQueryService.findByPortfolioAndId(portfolioId, providerId);
        if (provider.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var providerResource = ProviderContactResourceFromEntityAssembler.toResourceFromEntity(provider.get());
        return ResponseEntity.ok(providerResource);
    }

    // Obtener todos los empleados de un portfolio
    @GetMapping("/{portfolioId}/employees")
    public ResponseEntity<List<EmployeeContactResource>> getEmployeesByPortfolio(@PathVariable Long portfolioId) {
        var getPortfolioByIdQuery = new GetPortfolioByIdQuery(portfolioId);
        var portfolio = portfolioQueryService.handle(getPortfolioByIdQuery);
        if (portfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var employees = portfolio.get().getEmployees()
                .stream()
                .map(EmployeeContactResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(employees);
    }

    // Obtener un empleado específico dentro del portfolio
    @GetMapping("/{portfolioId}/employees/{employeeId}")
    public ResponseEntity<EmployeeContactResource> getEmployeeById(@PathVariable Long portfolioId,
                                                                   @PathVariable Long employeeId) {
        var employee = employeeContactQueryService.findByPortfolioAndId(portfolioId, employeeId);
        if (employee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var employeeResource = EmployeeContactResourceFromEntityAssembler.toResourceFromEntity(employee.get());
        return ResponseEntity.ok(employeeResource);
    }



}
