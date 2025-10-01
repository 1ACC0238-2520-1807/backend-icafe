package com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PortfolioRepository extends JpaRepository<ContactPortfolio, Long> {
    boolean existsContactPortfolioById(Long id);
}
