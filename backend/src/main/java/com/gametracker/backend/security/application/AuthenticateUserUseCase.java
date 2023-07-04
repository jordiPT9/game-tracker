package com.gametracker.backend.security.application;

import com.gametracker.backend.security.infrastructure.entrypoints.AuthenticateUserResponse;

public interface AuthenticateUserUseCase {
    AuthenticateUserResponse execute(AuthenticateUserCommand command);
}
