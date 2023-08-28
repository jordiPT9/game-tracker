package com.gametracker.backend.auth.infrastructure.endpoints;

import com.gametracker.backend.auth.application.LoginRequest;
import com.gametracker.backend.auth.application.LoginResponse;
import com.gametracker.backend.auth.application.LoginUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final LoginUseCaseImpl logInUseCase;

    public LoginController(LoginUseCaseImpl logInUseCase) {
        this.logInUseCase = logInUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> execute(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = logInUseCase.execute(loginRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
