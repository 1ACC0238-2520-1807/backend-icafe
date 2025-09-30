package com.synccafe.icafe.contacs.domain.service;

import com.synccafe.icafe.contacs.domain.model.commands.CreateEmployeeContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.EmployeeContact;

import java.util.Optional;

public interface EmployeeContactCommandService {
    Optional<EmployeeContact> handle(CreateEmployeeContactCommand command);
}
