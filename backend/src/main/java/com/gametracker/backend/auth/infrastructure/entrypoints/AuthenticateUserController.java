package com.gametracker.backend.auth.infrastructure.entrypoints;

import com.gametracker.backend.auth.application.AuthenticateUserCommand;
import com.gametracker.backend.auth.application.AuthenticateUserUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticateUserController {
    private final AuthenticateUserUseCaseImpl authenticationUseCaseImpl;

    public AuthenticateUserController(AuthenticateUserUseCaseImpl authenticationUseCaseImpl) {
        this.authenticationUseCaseImpl = authenticationUseCaseImpl;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateUserResponse> execute(@RequestBody AuthenticateUserRequest authenticateUserRequest) {
        AuthenticateUserCommand command = new AuthenticateUserCommand(authenticateUserRequest.username(), authenticateUserRequest.password());
        AuthenticateUserResponse response = authenticationUseCaseImpl.execute(command);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
