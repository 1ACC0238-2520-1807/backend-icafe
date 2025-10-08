package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.commands.UpdateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.EmployeeContactRepository;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.ProviderContactRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioCommandServiceImpl implements PortfolioCommandService {
    private final PortfolioRepository portfolioRepository;
    private final ProviderContactRepository providerContactRepository;
    private final EmployeeContactRepository employeeContactRepository;

    public PortfolioCommandServiceImpl(PortfolioRepository portfolioRepository, ProviderContactRepository providerContactRepository, EmployeeContactRepository employeeContactRepository) {
        this.portfolioRepository = portfolioRepository;
        this.providerContactRepository = providerContactRepository;
        this.employeeContactRepository = employeeContactRepository;
    }

    @Override
    public Optional<ContactPortfolio> handle(CreatePortfolioCommand command) {
        if(portfolioRepository.existsContactPortfolioById(command.userId())){
            throw new IllegalArgumentException("Portfolio with user ID " + command.userId() + " already exists.");
        } else {
            var contactPortfolio = new ContactPortfolio(command.userId());
            portfolioRepository.save(contactPortfolio);
            return Optional.of(contactPortfolio);
        }
    }

    @Override
    public ProviderContact addProviderToPortfolio(Long portfolioId, CreateProviderContactCommand command) {
        var portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio with ID " + portfolioId + " does not exist.");
        }
        var portfolioEntity = portfolio.get();
        var provider = new ProviderContact(command);
        provider.setPortfolio(portfolioEntity);
        portfolioEntity.addProvider(provider);
       // portfolioRepository.save(portfolioEntity);
       providerContactRepository.saveAndFlush(provider);
        return provider;
    }


    @Override
    public EmployeeContact addEmployeeToPortfolio(Long portfolioId, CreateEmployeeContactCommand command){
        var portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio with ID " + portfolioId + " does not exist.");
        } else {
            var portfolioEntity = portfolio.get();
            var employee = new EmployeeContact(command.name(), command.email(), command.phoneNumber(), command.role(),command.salary(), command.branchId());
            employee.setPortfolio(portfolioEntity);
            portfolioEntity.addEmployee(employee);
            //portfolioRepository.save(portfolioEntity);
            employeeContactRepository.saveAndFlush(employee);
            return employee;
        }
    }

    //Provider

    @Override
    public ProviderContact updateProviderInPortfolio(Long portfolioId, Long providerId, UpdateProviderContactCommand command) {
        var portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio with ID " + portfolioId + " does not exist.");
        }
        var providerOpt = providerContactRepository.findById(providerId);
        if (providerOpt.isEmpty()) {
            throw new IllegalArgumentException("Provider with ID " + providerId + " does not exist.");
        }
        var provider = providerOpt.get();
        if (!provider.getPortfolio().getId().equals(portfolioId)) {
            throw new IllegalArgumentException("Provider with ID " + providerId + " does not belong to Portfolio with ID " + portfolioId);
        }
        provider.setNameCompany(command.nameCompany());
        provider.setEmail(command.email());
        provider.setPhoneNumber(command.phoneNumber());
        provider.setRuc(command.ruc());
        providerContactRepository.save(provider);
        return provider;
    }

    @Override
    public boolean deleteProviderFromPortfolio(Long portfolioId, Long providerId) {
        var portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio with ID " + portfolioId + " does not exist.");
        }
        var portfolioEntity = portfolio.get();
        // Buscar proveedor dentro del portfolio
        var providerOpt = portfolioEntity.getProviders().stream()
                .filter(p -> p.getId().equals(providerId))
                .findFirst();

        if (providerOpt.isEmpty()) {
            return false; // no existe ese proveedor en este portfolio
        }

        var provider = providerOpt.get();

        // Eliminar relación y proveedor
        portfolioEntity.getProviders().remove(provider);
        providerContactRepository.delete(provider);

        // Guardar cambios en el portfolio
        portfolioRepository.save(portfolioEntity);

        return true;
    }

    //Employee
    @Override
    public EmployeeContact updateEmployeeInPortfolio(Long portfolioId, Long employeeId, CreateEmployeeContactCommand command) {
        var portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio with ID " + portfolioId + " does not exist.");
        }
        var employeeOpt = employeeContactRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        }
        var employee = employeeOpt.get();
        if (!employee.getPortfolio().getId().equals(portfolioId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not belong to Portfolio with ID " + portfolioId);
        }
        employee.setName(command.name());
        employee.setEmail(command.email());
        employee.setPhoneNumber(command.phoneNumber());
        employee.setRole(command.role());
        employee.setSalary(command.salary());
        employee.setBranchId(command.branchId());
        employeeContactRepository.save(employee);
        return employee;
        }

    @Override
    public boolean deleteEmployeeFromPortfolio(Long portfolioId, Long employeeId) {
        var portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio with ID " + portfolioId + " does not exist.");
        }
        var portfolioEntity = portfolio.get();
        // Buscar empleado dentro del portfolio
        var employeeOpt = portfolioEntity.getEmployees().stream()
                .filter(e -> e.getId().equals(employeeId))
                .findFirst();
        if (employeeOpt.isEmpty()) {
            return false; // no existe ese empleado en este portfolio
        }
        var employee = employeeOpt.get();
        // Eliminar relación y empleado
        portfolioEntity.getEmployees().remove(employee);
        employeeContactRepository.delete(employee);
        // Guardar cambios en el portfolio
        portfolioRepository.save(portfolioEntity);
        return true;
    }


}
