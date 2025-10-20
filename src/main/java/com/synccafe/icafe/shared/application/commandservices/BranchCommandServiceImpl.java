package com.synccafe.icafe.shared.application.commandservices;

import com.synccafe.icafe.shared.domain.model.commands.CreateBranchCommand;
import com.synccafe.icafe.shared.domain.model.commands.DeleteBranchCommand;
import com.synccafe.icafe.shared.domain.model.commands.UpdateBranchCommand;
import com.synccafe.icafe.shared.domain.model.entities.Branch;
import com.synccafe.icafe.shared.domain.model.valueobjects.OwnerId;
import com.synccafe.icafe.shared.domain.services.BranchCommandService;
import com.synccafe.icafe.shared.infrastructure.persistence.jpa.repositories.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BranchCommandServiceImpl implements BranchCommandService {

    private final BranchRepository branchRepository;

    public BranchCommandServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Optional<Branch> handle(CreateBranchCommand command) {

        // OwnerId ownerId = new OwnerId(command.ownerId());
        //if(branchRepository.existsBranchesByOwnerId(ownerId)){
          //  throw new IllegalArgumentException("Branch for owner ID " + command.ownerId() + " already exists.");
        //}
        var branch = new Branch(command);
        branchRepository.save(branch);
        return Optional.of(branch);
    }

    @Override
    public Optional<Branch> handle(Long branchId, UpdateBranchCommand command) {
       var branch= branchRepository.findById(branchId);
         if(branch.isEmpty()){
              throw new IllegalArgumentException("Branch with ID " + branchId + " does not exist.");
         }
         branch.get().updateBranch(command);
         branchRepository.save(branch.get());
         return Optional.of(branch.get());
    }

    @Override
    public Optional<Branch> handle(DeleteBranchCommand command) {
        var branch = branchRepository.findById(command.branchId());
        if(branch.isEmpty()){
            throw new IllegalArgumentException("Branch with ID " + command.branchId() + " does not exist.");
        }
        branchRepository.delete(branch.get());
        return Optional.of(branch.get());
    }
}
