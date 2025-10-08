package com.synccafe.icafe.contacs.domain.model.commands;

public record UpdateEmployeeContactCommand(String name, String email,String phoneNumber, String role,String salary,Long branchId) {
}
