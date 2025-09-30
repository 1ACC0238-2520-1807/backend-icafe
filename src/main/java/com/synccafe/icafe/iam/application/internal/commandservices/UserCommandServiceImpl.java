package com.synccafe.icafe.iam.application.internal.commandservices;

import com.synccafe.icafe.iam.application.internal.outboundservices.hashing.HashingService;
import com.synccafe.icafe.iam.application.internal.outboundservices.tokens.TokenService;
import com.synccafe.icafe.iam.domain.model.aggregates.User;
import com.synccafe.icafe.iam.domain.model.commands.SignInCommand;
import com.synccafe.icafe.iam.domain.model.commands.SignUpCommand;
import com.synccafe.icafe.iam.domain.model.entities.Role;
import com.synccafe.icafe.iam.domain.model.valueobjects.Roles;
import com.synccafe.icafe.iam.domain.service.UserCommandService;
import com.synccafe.icafe.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.synccafe.icafe.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Email already exists");
        var stringRoles = command.roles();
        var roles = new ArrayList<Role>();
        if (stringRoles == null || stringRoles.isEmpty()) {
            var storedRole = roleRepository.findByName(Roles.OWNER);
            storedRole.ifPresent(roles::add);
        } else {
            stringRoles.forEach(role -> {
                var storedRole = roleRepository.findByName(Roles.valueOf(role));
                storedRole.ifPresent(roles::add);
            });
        }
        var user = new User(command.email(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findByEmail(command.email());
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }
}
