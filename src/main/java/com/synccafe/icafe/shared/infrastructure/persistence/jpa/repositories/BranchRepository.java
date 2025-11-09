package com.synccafe.icafe.shared.infrastructure.persistence.jpa.repositories;


import com.synccafe.icafe.shared.domain.model.entities.Branch;
import com.synccafe.icafe.shared.domain.model.valueobjects.OwnerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findById(Long id);
    List<Branch> findAllByOwnerId(OwnerId ownerId);
    boolean existsBranchesByOwnerId(OwnerId ownerId);
    boolean existsBranchesByIdAndOwnerId(Long id, OwnerId ownerId);
    boolean existsBranchesById(Long id);


}
