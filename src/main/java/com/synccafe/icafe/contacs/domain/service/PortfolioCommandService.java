package com.synccafe.icafe.contacs.domain.service;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.domain.model.commands.UpdateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;

import java.util.Optional;

public interface PortfolioCommandService {
    Optional<ContactPortfolio> handle(CreatePortfolioCommand command);
    ProviderContact addProviderToPortfolio(Long portfolioId, CreateProviderContactCommand command);
    EmployeeContact addEmployeeToPortfolio(Long portfolioId, CreateEmployeeContactCommand command);
    //Provider
    ProviderContact updateProviderInPortfolio(Long portfolioId, Long providerId, UpdateProviderContactCommand command);
    boolean deleteProviderFromPortfolio(Long portfolioId, Long providerId);

    //Employee
    EmployeeContact updateEmployeeInPortfolio(Long portfolioId, Long employeeId, CreateEmployeeContactCommand command);
    boolean deleteEmployeeFromPortfolio(Long portfolioId, Long employeeId);
}
