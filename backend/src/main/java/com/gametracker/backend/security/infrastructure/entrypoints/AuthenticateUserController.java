package com.gametracker.backend.security.infrastructure.entrypoints;

import com.gametracker.backend.security.application.AuthenticateUserCommand;
import com.gametracker.backend.security.application.AuthenticateUserUseCaseImpl;
import com.gametracker.backend.security.domain.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> execute(@RequestBody AuthenticateUserRequest authenticateUserRequest) {
        AuthenticateUserCommand command = new AuthenticateUserCommand(authenticateUserRequest.username(), authenticateUserRequest.password());

        AuthenticateUserResponse response;
        try {
            response = authenticationUseCaseImpl.execute(command);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
