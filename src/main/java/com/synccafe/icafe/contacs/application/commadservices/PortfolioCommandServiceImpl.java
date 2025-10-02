package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.ProviderContactRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioCommandServiceImpl implements PortfolioCommandService {
    private final PortfolioRepository portfolioRepository;
    private final ProviderContactRepository providerContactRepository;

    public PortfolioCommandServiceImpl(PortfolioRepository portfolioRepository, ProviderContactRepository providerContactRepository) {
        this.portfolioRepository = portfolioRepository;
        this.providerContactRepository = providerContactRepository;
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
        portfolioRepository.save(portfolioEntity);
        providerContactRepository.save(provider);
        return provider;
    }

}
