package com.gametracker.backend.user.infrastructure.endpoints.Register;

public record RegisterUserRequestBody(
    String id, String username, String password, String email, String role) {}
