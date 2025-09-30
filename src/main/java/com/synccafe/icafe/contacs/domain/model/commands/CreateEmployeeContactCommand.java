package com.synccafe.icafe.contacs.domain.model.commands;

import com.synccafe.icafe.contacs.domain.model.valueobjects.Role_Employee;

import java.util.List;

public record CreateEmployeeContactCommand(String name, String email,String phoneNumber, List<String> roles) {
}
