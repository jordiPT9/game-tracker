package com.gametracker.backend.user.infrastructure.endpoints.UpdateEmail;

import com.gametracker.backend.user.application.update_email.UpdateEmailUserRequest;
import com.gametracker.backend.user.application.update_email.UpdateEmailUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UpdateEmailUserController {
    private final UpdateEmailUserUseCase updateEmailUserUseCase;

    public UpdateEmailUserController(UpdateEmailUserUseCase updateEmailUserUseCase) {
        this.updateEmailUserUseCase = updateEmailUserUseCase;
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<HttpStatus> execute(@PathVariable String username, @RequestParam String email) {
        UpdateEmailUserRequest request = new UpdateEmailUserRequest(username, email);
        updateEmailUserUseCase.execute(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
