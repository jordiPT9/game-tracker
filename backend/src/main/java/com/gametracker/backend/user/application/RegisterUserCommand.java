package com.gametracker.backend.user.application;

public record RegisterUserCommand(
        String id,
        String username,
        String password,
        String email,
        String role
) {
}
