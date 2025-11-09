package com.synccafe.icafe.shared.domain.services;

import com.synccafe.icafe.shared.domain.model.entities.Branch;
import com.synccafe.icafe.shared.domain.model.queries.GetAllBranchByOwnerIdQuery;
import com.synccafe.icafe.shared.domain.model.queries.GetAllBranchQuery;
import com.synccafe.icafe.shared.domain.model.queries.GetBranchByIdQuery;

import java.util.List;
import java.util.Optional;

public interface BranchQueryService {
    List<Branch> handle(GetAllBranchQuery query);
    Optional<Branch> handle(GetBranchByIdQuery query);

    List<Branch> handle(GetAllBranchByOwnerIdQuery query);

}
