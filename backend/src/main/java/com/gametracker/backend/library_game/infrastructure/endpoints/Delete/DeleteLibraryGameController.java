package com.gametracker.backend.library_game.infrastructure.endpoints.Delete;

import com.gametracker.backend.library_game.application.delete_library_game.DeleteLibraryGameCommand;
import com.gametracker.backend.library_game.application.delete_library_game.DeleteLibraryGameUseCase;
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
  public ResponseEntity<HttpStatus> execute(
      @PathVariable String libraryGameId, Principal principal) {
    DeleteLibraryGameCommand command =
        new DeleteLibraryGameCommand(libraryGameId, principal.getName());
    deleteLibraryGameUseCase.execute(command);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
