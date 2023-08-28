package com.gametracker.backend.auth.application;

public interface LoginUseCase {
    LoginResponse execute(LoginRequest request);
}
