package com.synccafe.icafe.contacs.interfaces.rest.transform;

import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.interfaces.rest.resources.ProviderContactResource;

public class ProviderContactResourceFromEntityAssembler {
    public static ProviderContactResource toResourceFromEntity(ProviderContact entity) {
        return new ProviderContactResource(
                entity.getId(),
                entity.getNameCompany(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getRuc()
        );
    }
}
