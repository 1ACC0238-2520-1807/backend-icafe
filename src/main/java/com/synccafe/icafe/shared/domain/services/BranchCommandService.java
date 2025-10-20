package com.synccafe.icafe.shared.domain.services;

import com.synccafe.icafe.shared.domain.model.commands.CreateBranchCommand;
import com.synccafe.icafe.shared.domain.model.commands.DeleteBranchCommand;
import com.synccafe.icafe.shared.domain.model.commands.UpdateBranchCommand;
import com.synccafe.icafe.shared.domain.model.entities.Branch;

import java.util.Optional;

public interface BranchCommandService {
    Optional<Branch> handle(CreateBranchCommand command);
    Optional<Branch> handle(Long branchId,UpdateBranchCommand command);
    Optional<Branch> handle(DeleteBranchCommand command);
}
