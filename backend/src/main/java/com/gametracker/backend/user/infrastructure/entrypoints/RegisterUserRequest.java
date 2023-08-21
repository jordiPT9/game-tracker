package com.gametracker.backend.user.infrastructure.entrypoints;

public record RegisterUserRequest(
        String id,
        String username,
        String password,
        String email,
        String role
) {
}
