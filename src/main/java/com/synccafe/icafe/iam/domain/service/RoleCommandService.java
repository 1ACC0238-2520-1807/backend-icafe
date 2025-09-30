package com.synccafe.icafe.iam.domain.service;

import com.synccafe.icafe.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
