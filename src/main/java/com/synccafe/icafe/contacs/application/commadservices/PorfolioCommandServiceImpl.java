package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PorfolioCommandServiceImpl implements PortfolioCommandService {
    private final PortfolioRepository portfolioRepository;

    public PorfolioCommandServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
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
}
