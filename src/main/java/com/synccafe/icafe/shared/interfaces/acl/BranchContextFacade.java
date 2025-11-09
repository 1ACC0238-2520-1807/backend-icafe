package com.synccafe.icafe.shared.interfaces.acl;

import com.synccafe.icafe.shared.domain.model.commands.CreateBranchCommand;
import com.synccafe.icafe.shared.domain.model.queries.GetBranchByIdQuery;
import com.synccafe.icafe.shared.domain.services.BranchCommandService;
import com.synccafe.icafe.shared.domain.services.BranchQueryService;
import org.springframework.stereotype.Service;

import static org.springframework.boot.autoconfigure.container.ContainerImageMetadata.isPresent;

@Service
public class BranchContextFacade {
    private final BranchCommandService branchCommandService;
    private final BranchQueryService branchQueryService;

    public BranchContextFacade(BranchCommandService branchCommandService, BranchQueryService branchQueryService) {
        this.branchCommandService = branchCommandService;
        this.branchQueryService = branchQueryService;
    }

    //Creas y retornas un branch
    public Long createBranch(CreateBranchCommand command){
        var branch= branchCommandService.handle(command);
        if (branch.isEmpty()){
            return 0L;
        }
        return branch.get().getId();
    }

    //verifica si el branch existe
    public boolean existsBranch(Long branchId){
        return branchQueryService.handle(new GetBranchByIdQuery(branchId)).isPresent();
    }

    //Retorna un branch por Id
    public Long getBranchById(Long branchId){
        var branch= branchQueryService.handle(new GetBranchByIdQuery(branchId));
        if(branch.isEmpty()){
            return 0L;
        }
        return branch.get().getId();
    }

    //Crear Branch default para un ownerId y retornar su Id
    public Long createDefaultBranchForOwner(Long ownerId){
        var command= new CreateBranchCommand(ownerId,"Default Branch","Default Address");
        var branch= branchCommandService.handle(command);
        if (branch.isEmpty()){
            return 0L;
        }
        return branch.get().getId();
    }
}
