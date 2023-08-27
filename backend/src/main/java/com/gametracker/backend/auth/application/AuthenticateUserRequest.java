package com.gametracker.backend.auth.application;

public record AuthenticateUserRequest(
        String username,
        String password
) {
}
