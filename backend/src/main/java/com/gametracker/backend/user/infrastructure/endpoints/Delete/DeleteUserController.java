package com.gametracker.backend.user.infrastructure.endpoints.Delete;

import com.gametracker.backend.user.application.delete_user.DeleteUserRequest;
import com.gametracker.backend.user.application.delete_user.DeleteUserUseCase;
import com.gametracker.backend.user.application.register_user.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class DeleteUserController {
    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<HttpStatus> execute(Principal principal, @PathVariable String userId) {
        DeleteUserRequest request = new DeleteUserRequest(userId, principal.getName());
        deleteUserUseCase.execute(request);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
