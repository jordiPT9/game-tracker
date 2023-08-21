package com.gametracker.backend.user.infrastructure.entrypoints;

import com.gametracker.backend.user.application.RegisterUserCommand;
import com.gametracker.backend.user.application.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterUserController {
    private final RegisterUserUseCase registerUserUseCase;

    public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> execute(@RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.id(),
                request.username(),
                request.password(),
                request.email(),
                request.role());
        registerUserUseCase.execute(command);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
