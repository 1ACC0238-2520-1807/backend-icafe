package com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeContactRepository extends JpaRepository<EmployeeContact, Long> {
    Optional<EmployeeContact> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<EmployeeContact> findByIdAndPortfolioId(Long id, Long portfolioId);
}
