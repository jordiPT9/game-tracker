package com.gametracker.backend.steps;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.role.domain.RoleRepository;
import io.cucumber.java.en.Given;

import java.util.List;

public class RoleSteps {
    private final RoleRepository roleRepository;

    public RoleSteps(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Given("the following roles exist:")
    public void theFollowingRolesExist(List<String> roles) {
        roles.forEach(role -> {
            roleRepository.save(RoleName.valueOf(role));
        });
    }
}
