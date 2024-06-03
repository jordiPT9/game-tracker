package com.gametracker.backend.user.infrastructure.endpoints.Register;

import com.gametracker.backend.user.application.register_user.RegisterUserRequest;
import com.gametracker.backend.user.application.register_user.RegisterUserUseCase;
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
  public ResponseEntity<HttpStatus> execute(@RequestBody RegisterUserRequestBody requestBody) {
    RegisterUserRequest request =
        new RegisterUserRequest(
            requestBody.id(), requestBody.username(), requestBody.password(), requestBody.email(), requestBody.role());
    registerUserUseCase.execute(request);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
