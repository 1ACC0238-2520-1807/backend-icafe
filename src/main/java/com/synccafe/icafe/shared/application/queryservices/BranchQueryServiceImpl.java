package com.synccafe.icafe.shared.application.queryservices;

import com.synccafe.icafe.shared.domain.model.entities.Branch;
import com.synccafe.icafe.shared.domain.model.queries.GetAllBranchByOwnerIdQuery;
import com.synccafe.icafe.shared.domain.model.queries.GetAllBranchQuery;
import com.synccafe.icafe.shared.domain.model.queries.GetBranchByIdQuery;
import com.synccafe.icafe.shared.domain.model.valueobjects.OwnerId;
import com.synccafe.icafe.shared.domain.services.BranchQueryService;
import com.synccafe.icafe.shared.infrastructure.persistence.jpa.repositories.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchQueryServiceImpl implements BranchQueryService {

    private final BranchRepository branchRepository;

    public BranchQueryServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public List<Branch> handle(GetAllBranchQuery query) {
        return branchRepository.findAll();
    }

    @Override
    public Optional<Branch> handle(GetBranchByIdQuery query) {
        return branchRepository.findById(query.branchId());
    }

    @Override
    public List<Branch> handle(GetAllBranchByOwnerIdQuery query) {
        OwnerId ownerId = new OwnerId(query.ownerId());
        return branchRepository.findAllByOwnerId(ownerId);
    }
}
