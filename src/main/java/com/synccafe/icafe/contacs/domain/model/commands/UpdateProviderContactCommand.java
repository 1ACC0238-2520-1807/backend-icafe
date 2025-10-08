package com.synccafe.icafe.contacs.domain.model.commands;

public record UpdateProviderContactCommand(String nameCompany, String email, String phoneNumber, String ruc) {
}
