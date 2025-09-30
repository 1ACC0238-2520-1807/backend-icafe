package com.synccafe.icafe.contacs.interfaces.rest.resources;

import java.util.List;

public record CreateEmployeeContactResource(
        String name,
        String email,
        String phoneNumber,
        List<String> roles,
        Long branchId
) {
}
