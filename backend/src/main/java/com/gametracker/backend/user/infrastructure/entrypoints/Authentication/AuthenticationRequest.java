package com.gametracker.backend.user.infrastructure.entrypoints.Authentication;

public record AuthenticationRequest(String username, String password) {
}
