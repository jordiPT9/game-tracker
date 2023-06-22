package com.gametracker.backend.user.application;

public record AuthenticationCommand(String username, String password) {
}
