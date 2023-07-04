package com.gametracker.backend.auth.infrastructure.entrypoints;

public record AuthenticateUserRequest(String username, String password) {
}
