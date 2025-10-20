package com.synccafe.icafe.shared.interfaces.rest.resources;

public record CreateBranchResource(
    Long ownerId,
    String name,
    String address
) {
}
