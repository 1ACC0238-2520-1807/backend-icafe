package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.domain.service.EmployeeContactCommandService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.EmployeeContactRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeContactCommandServiceImpl implements EmployeeContactCommandService {

    private final EmployeeContactRepository employeeContactRepository;

    public EmployeeContactCommandServiceImpl(EmployeeContactRepository employeeContactRepository) {
        this.employeeContactRepository = employeeContactRepository;
    }

    @Override
    public Optional<EmployeeContact> handle(CreateEmployeeContactCommand command) {
        if (employeeContactRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Employee with email " + command.email() + " already exists.");
        }
            var employeeContact = new EmployeeContact(command.name(), command.email(), command.phoneNumber(), command.role(),command.salary(), command.branchId());
            employeeContactRepository.save(employeeContact);
            return Optional.of(employeeContact);
        }
}
