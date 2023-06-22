package com.gametracker.backend.user.infrastructure.entrypoints.Authentication;

import com.gametracker.backend.user.application.AuthenticationCommand;
import com.gametracker.backend.user.application.AuthenticationUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse execute(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationCommand command = new AuthenticationCommand(authenticationRequest.username(), authenticationRequest.password());
        AuthenticationResponse response = authenticationUseCase.execute(command);

        return response;
    }
}
