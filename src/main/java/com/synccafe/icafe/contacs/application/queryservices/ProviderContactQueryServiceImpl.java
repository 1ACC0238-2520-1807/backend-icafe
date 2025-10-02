package com.synccafe.icafe.contacs.application.queryservices;

import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.model.queries.GetAllProviderContactQuery;
import com.synccafe.icafe.contacs.domain.model.queries.GetProviderContactByIdQuery;
import com.synccafe.icafe.contacs.domain.service.ProviderContactQueryService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.ProviderContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderContactQueryServiceImpl implements ProviderContactQueryService {
    private final ProviderContactRepository providerContactRepository;

    public ProviderContactQueryServiceImpl(ProviderContactRepository providerContactRepository) {
        this.providerContactRepository = providerContactRepository;
    }

    @Override
    public List<ProviderContact> handle(GetAllProviderContactQuery query) {
        return providerContactRepository.findAll();
    }

    @Override
    public Optional<ProviderContact> handle(GetProviderContactByIdQuery query) {
        return providerContactRepository.findById(query.id());
    }

    @Override
    public Optional<ProviderContact> findByPortfolioAndId(Long portfolioId, Long providerId) {
        return providerContactRepository.findByIdAndPortfolioId(providerId, portfolioId);
    }
}
