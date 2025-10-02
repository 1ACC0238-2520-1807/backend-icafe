package com.synccafe.icafe.contacs.domain.service;

import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.model.queries.GetAllProviderContactQuery;
import com.synccafe.icafe.contacs.domain.model.queries.GetProviderContactByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProviderContactQueryService {
    List<ProviderContact> handle(GetAllProviderContactQuery query);
    Optional<ProviderContact> handle(GetProviderContactByIdQuery query);

    Optional<ProviderContact> findByPortfolioAndId(Long portfolioId, Long providerId);
}
