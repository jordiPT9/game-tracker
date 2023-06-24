package com.gametracker.backend.user.application;

import com.gametracker.backend.user.infrastructure.entrypoints.Authentication.AuthenticationResponse;

public interface AuthenticationUseCase {
    AuthenticationResponse execute(AuthenticationCommand command);
}
