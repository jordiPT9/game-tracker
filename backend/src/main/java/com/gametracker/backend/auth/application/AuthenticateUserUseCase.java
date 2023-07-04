package com.gametracker.backend.auth.application;

import com.gametracker.backend.auth.infrastructure.entrypoints.AuthenticateUserResponse;

public interface AuthenticateUserUseCase {
    AuthenticateUserResponse execute(AuthenticateUserCommand command);
}
