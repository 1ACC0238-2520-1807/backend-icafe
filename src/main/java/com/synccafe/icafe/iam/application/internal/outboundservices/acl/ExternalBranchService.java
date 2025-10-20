package com.synccafe.icafe.iam.application.internal.outboundservices.acl;

import com.synccafe.icafe.shared.interfaces.acl.BranchContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalBranchService {
    private final BranchContextFacade  branchContextFacade;

    public ExternalBranchService(BranchContextFacade branchContextFacade) {
        this.branchContextFacade = branchContextFacade;
    }

    //Crear un branch default para un usuario
    public void CreateDefaultBranchForUser(Long ownerId){
        branchContextFacade.createDefaultBranchForOwner(ownerId);
    }

}
