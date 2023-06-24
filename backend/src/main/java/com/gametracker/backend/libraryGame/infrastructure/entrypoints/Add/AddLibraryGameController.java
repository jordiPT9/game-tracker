package com.gametracker.backend.libraryGame.infrastructure.entrypoints.Add;

import com.gametracker.backend.libraryGame.application.Add.AddLibraryGameCommand;
import com.gametracker.backend.libraryGame.application.Add.AddLibraryGameUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AddLibraryGameController {
    private final AddLibraryGameUseCase addLibraryGameUseCase;

    public AddLibraryGameController(AddLibraryGameUseCase addLibraryGameUseCase) {
        this.addLibraryGameUseCase = addLibraryGameUseCase;
    }

    @PostMapping("/library-games")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AddLibraryGameRequest request, Principal principal) {
        AddLibraryGameCommand command = new AddLibraryGameCommand(
                request.id(),
                request.title(),
                request.rating(),
                request.status(),
                principal.getName()
        );
        addLibraryGameUseCase.execute(command);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
