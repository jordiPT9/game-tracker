package com.gametracker.backend.auth.application;

public interface AuthenticateUserUseCase {
    AuthenticateUserResponse execute(AuthenticateUserRequest request);
}
