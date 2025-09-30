package com.synccafe.icafe.contacs.domain.service;

import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;

import java.util.Optional;

public interface ProviderContactCommandService {
    Optional<ProviderContact> handle(CreateProviderContactCommand command);
}
