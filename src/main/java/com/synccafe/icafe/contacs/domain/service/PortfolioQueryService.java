package com.synccafe.icafe.contacs.domain.service;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.queries.GetAllPortfoliosQuery;
import com.synccafe.icafe.contacs.domain.model.queries.GetPortfolioByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PortfolioQueryService {
    List<ContactPortfolio> handle(GetAllPortfoliosQuery query);
    Optional<ContactPortfolio> handle(GetPortfolioByIdQuery query);
}
