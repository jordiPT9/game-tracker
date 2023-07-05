package com.gametracker.backend.steps;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserSteps {
    private final UserRepository userRepository;

    public UserSteps(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DataTableType
    public User userTransformer(Map<String, String> entry) {
        return new User(
                entry.get("id"),
                entry.get("username"),
                entry.get("password"),
                entry.get("email"),
                RoleName.valueOf(entry.get("role"))
        );
    }

    @Given("the following users exist:")
    public void theFollowingUsersExist(List<User> users) {
        users.forEach(userRepository::save);
    }

    @And("users with the following usernames should be in the database:")
    public void aUserWithIdShouldBeInTheDatabase(List<String> usernames) {
        usernames.forEach(username -> {
            User user = userRepository.findByUsername(username);
            assertNotNull(user);
        });
    }
}
