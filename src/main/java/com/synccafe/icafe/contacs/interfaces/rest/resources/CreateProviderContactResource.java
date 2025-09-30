package com.synccafe.icafe.contacs.interfaces.rest.resources;

public record CreateProviderContactResource(
        String nameCompany,
        String email,
        String phoneNumber,
        String ruc
) {
}
