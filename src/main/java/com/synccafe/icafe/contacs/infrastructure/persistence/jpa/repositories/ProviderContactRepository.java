package com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories;

import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderContactRepository extends JpaRepository<ProviderContact, Long> {
    Optional<ProviderContact> findByEmail(String email);
    Optional<ProviderContact> findByRuc(String ruc);
    boolean existsByEmail(String email);
    boolean existsByRuc(String ruc);
}
