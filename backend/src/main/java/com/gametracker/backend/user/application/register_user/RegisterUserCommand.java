package com.gametracker.backend.user.application.register_user;

public record RegisterUserCommand(
        String id,
        String username,
        String password,
        String email,
        String role
) {
}
