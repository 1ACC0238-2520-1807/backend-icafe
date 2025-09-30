package com.synccafe.icafe.contacs.domain.model.commands;

import java.util.List;

public record CreateEmployeeContactCommand(String name, String email,String phoneNumber, List<String> roles,Long branchId) {
}
