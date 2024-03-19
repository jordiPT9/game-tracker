package com.gametracker.backend.user.domain;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("User not found with username '" + username + "'");
  }
}
