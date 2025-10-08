package com.synccafe.icafe.contacs.domain.service;



import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;

import java.util.Optional;

public interface EmployeeContactQueryService {
    Optional<EmployeeContact> findByPortfolioAndId(Long portfolioId, Long employeeId);
}
