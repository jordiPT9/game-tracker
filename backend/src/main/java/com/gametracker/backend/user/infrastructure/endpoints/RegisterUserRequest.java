package com.gametracker.backend.user.infrastructure.endpoints;

public record RegisterUserRequest(
    String id, String username, String password, String email, String role) {}
