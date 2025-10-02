package com.synccafe.icafe.contacs.domain.service;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;

import java.util.Optional;

public interface PortfolioCommandService {
    Optional<ContactPortfolio> handle(CreatePortfolioCommand command);
    ProviderContact addProviderToPortfolio(Long portfolioId, CreateProviderContactCommand command);
}
