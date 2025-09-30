package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.service.ProviderContactCommandService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.ProviderContactRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProviderContactCommandServiceImpl implements ProviderContactCommandService {

    private final ProviderContactRepository providerContactRepository;

    public ProviderContactCommandServiceImpl(ProviderContactRepository providerContactRepository) {
        this.providerContactRepository = providerContactRepository;
    }

    @Override
    public Optional<ProviderContact> handle(CreateProviderContactCommand command) {

        if(providerContactRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Provider with email " + command.email() + " already exists.");
        }
        if(providerContactRepository.existsByRuc(command.ruc())) {
            throw new IllegalArgumentException("Provider with RUC " + command.ruc() + " already exists.");
        }
        var providerContact = new ProviderContact(command);
        providerContactRepository.save(providerContact);
        return Optional.of(providerContact);

    }
}
