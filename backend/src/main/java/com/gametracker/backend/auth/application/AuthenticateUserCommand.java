package com.gametracker.backend.auth.application;

public record AuthenticateUserCommand(
        String username,
        String password
) {
}
