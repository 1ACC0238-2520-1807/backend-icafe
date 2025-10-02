package com.synccafe.icafe.contacs.application.queryservices;

import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.domain.service.EmployeeContactQueryService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.EmployeeContactRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeContactQueryServicImpl implements EmployeeContactQueryService {
    private final EmployeeContactRepository employeeContactRepository;
    public EmployeeContactQueryServicImpl(EmployeeContactRepository employeeContactRepository) {
        this.employeeContactRepository = employeeContactRepository;
    }
    @Override
    public Optional<EmployeeContact> findByPortfolioAndId(Long portfolioId, Long employeeId) {
        return employeeContactRepository.findByIdAndPortfolioId(employeeId, portfolioId);
    }
}
