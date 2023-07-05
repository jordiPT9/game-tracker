package com.gametracker.backend.libraryGame.infrastructure.entrypoints.Delete;

import com.gametracker.backend.libraryGame.application.Delete.DeleteLibraryGameCommand;
import com.gametracker.backend.libraryGame.application.Delete.DeleteLibraryGameUseCase;
import com.gametracker.backend.libraryGame.domain.LibraryGameAccessDeniedException;
import com.gametracker.backend.libraryGame.domain.LibraryGameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class DeleteLibraryGameController {
    private final DeleteLibraryGameUseCase deleteLibraryGameUseCase;

    public DeleteLibraryGameController(DeleteLibraryGameUseCase deleteLibraryGameUseCase) {
        this.deleteLibraryGameUseCase = deleteLibraryGameUseCase;
    }

    @DeleteMapping("/library-games/{libraryGameId}")
    public ResponseEntity<?> execute(@PathVariable String libraryGameId, Principal principal) {
        DeleteLibraryGameCommand command = new DeleteLibraryGameCommand(
                libraryGameId,
                principal.getName()
        );

        try {
            deleteLibraryGameUseCase.execute(command);
        } catch (LibraryGameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (LibraryGameAccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}