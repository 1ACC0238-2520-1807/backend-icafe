package com.synccafe.icafe.contacs.interfaces.rest.resources;

import java.util.List;

public record EmployeeContactResource(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String role,
        String salary,
        Long branchId
) {
}
