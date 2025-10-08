package com.synccafe.icafe.contacs.interfaces.rest.resources;

public record UpdateProviderContactResource(
        String nameCompany,
        String email,
        String phoneNumber,
        String ruc
) {
}
