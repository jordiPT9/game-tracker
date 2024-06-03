package com.gametracker.backend.acceptance.steps;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps {
  private final UserRepository userRepository;

  public UserSteps(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @DataTableType
  public User userTransformer(Map<String, String> entry) {
    return User.builder()
        .id(entry.get("id"))
        .username(entry.get("username"))
        .password(entry.get("password"))
        .email(entry.get("email"))
        .role(RoleName.valueOf(entry.get("role")))
        .build();
  }

  @Given("the following users exist:")
  public void theFollowingUsersExist(List<User> users) {
    users.forEach(userRepository::save);
  }

  @Then("users with the following usernames should not be in the database:")
  public void aUserWithIdShouldNotBeInTheDatabase(List<String> usernames) {
    usernames.forEach(
            username -> {
              Optional<User> user = userRepository.findByUsername(username);
              assertFalse(user.isPresent());
            });
  }

  @Then("users with the following usernames should be in the database:")
  public void aUserWithIdShouldBeInTheDatabase(List<String> usernames) {
    usernames.forEach(
        username -> {
          Optional<User> user = userRepository.findByUsername(username);
          assertTrue(user.isPresent());
        });
  }
}
