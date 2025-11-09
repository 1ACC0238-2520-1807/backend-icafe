package com.synccafe.icafe.purchasing.interfaces.rest.transform;

import com.synccafe.icafe.contacs.interfaces.acl.ContactsContextFacade;
import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.interfaces.rest.resources.PurchaseOrderResource;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderResourceFromEntityAssembler {
    
    private final ContactsContextFacade contactsContextFacade;
    
    public PurchaseOrderResourceFromEntityAssembler(ContactsContextFacade contactsContextFacade) {
        this.contactsContextFacade = contactsContextFacade;
    }
    
    public PurchaseOrderResource toResourceFromEntity(PurchaseOrder entity) {
        var provider = contactsContextFacade.getProviderContactById(entity.getProviderId());
        
        return new PurchaseOrderResource(
                entity.getId(),
                entity.getBranchId().branchId(),
                entity.getProviderId(),
                provider != null ? provider.getNameCompany() : "Unknown Provider",
                provider != null ? provider.getEmail() : "",
                provider != null ? provider.getPhoneNumber() : "",
                provider != null ? provider.getRuc() : "",
                entity.getSupplyItemId(),
                entity.getUnitPrice().amount(),
                entity.getQuantity(),
                entity.getTotalAmount().amount(),
                entity.getPurchaseDate(),
                entity.getExpirationDate(),
                entity.getStatus(),
                entity.getNotes()
        );
    }
}