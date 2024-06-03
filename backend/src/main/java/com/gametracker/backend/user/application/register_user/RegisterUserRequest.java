package com.gametracker.backend.user.application.register_user;

public record RegisterUserRequest(
    String id, String username, String password, String email, String role) {}
