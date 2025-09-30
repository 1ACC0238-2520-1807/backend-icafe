package com.synccafe.icafe.iam.domain.model.commands;

import java.util.List;

public record SignUpCommand(String email, String password, List<String> roles) {
}
