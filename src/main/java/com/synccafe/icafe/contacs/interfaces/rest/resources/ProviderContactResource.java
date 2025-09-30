package com.synccafe.icafe.contacs.interfaces.rest.resources;

public record ProviderContactResource(
        Long id,
        String nameCompany,
        String email,
        String phoneNumber,
        String ruc
) {
}
