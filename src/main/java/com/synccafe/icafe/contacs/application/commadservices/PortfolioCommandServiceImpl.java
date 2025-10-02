package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.commands.UpdateProviderContactCommand;
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


}
