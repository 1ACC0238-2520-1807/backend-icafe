package com.synccafe.icafe.shared.domain.model.commands;

public record CreateBranchCommand( Long ownerId, String name, String address) {
}
