package com.synccafe.icafe.contacs.domain.model.commands;

public record CreateProviderContactCommand(String nameCompany, String email, String phoneNumber, String ruc) {
}
