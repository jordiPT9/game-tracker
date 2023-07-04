package com.gametracker.backend.security.application;

public record AuthenticateUserCommand(String username, String password) {
}
