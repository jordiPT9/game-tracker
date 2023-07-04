package com.gametracker.backend.security.infrastructure.entrypoints;

public record AuthenticateUserRequest(String username, String password) {
}
