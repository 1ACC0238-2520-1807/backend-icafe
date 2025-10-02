package com.synccafe.icafe.contacs.application.commadservices;

import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;
import com.synccafe.icafe.contacs.domain.service.EmployeeContactCommandService;
import com.synccafe.icafe.contacs.infrastructure.persistence.jpa.repositories.EmployeeContactRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmployeeContactCommandServiceImpl implements EmployeeContactCommandService {

    private final EmployeeContactRepository employeeContactRepository;
    private final RoleEmployeeRespository roleEmployeeRespository;

public EmployeeContactCommandServiceImpl(EmployeeContactRepository employeeContactRepository, RoleEmployeeRespository roleRepository) {
        this.employeeContactRepository = employeeContactRepository;
        this.roleEmployeeRespository = roleRepository;
    }

    @Override
    public Optional<EmployeeContact> handle(CreateEmployeeContactCommand command) {
        if(employeeContactRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Employee with email " + command.email() + " already exists.");
        }
        var stringRoles = command.roles();
        var roles = new ArrayList<RoleEmployee>();
        if ( stringRoles== null || stringRoles.isEmpty()){
            var storedRole=roleEmployeeRespository.findByName(RolesEmployee.BARISTA);
            storedRole.ifPresent(roles::add);
        }
        else {
            stringRoles.forEach(role -> {
                var storedRole=roleEmployeeRespository.findByName(RolesEmployee.valueOf(role));
                storedRole.ifPresent(roles::add);
            });
        }
        var employeeContact = new EmployeeContact(command.name(),command.email(), command.phoneNumber(),command.branchId(),roles);
        employeeContactRepository.save(employeeContact);
        return Optional.of(employeeContact);
    }
}
