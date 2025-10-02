package com.synccafe.icafe.contacs.application.queryservices;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.queries.GetAllPortfoliosQuery;
import com.synccafe.icafe.contacs.domain.model.queries.GetPortfolioByIdQuery;
import com.synccafe.icafe.contacs.domain.service.PortfolioQueryService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioQueryServiceImpl implements PortfolioQueryService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioQueryServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }
    @Override
    public List<ContactPortfolio> handle(GetAllPortfoliosQuery query) {
        return portfolioRepository.findAll();
    }

    @Override
    public Optional<ContactPortfolio> handle(GetPortfolioByIdQuery query) {
        return portfolioRepository.findById(query.portfolioId());
    }
}
