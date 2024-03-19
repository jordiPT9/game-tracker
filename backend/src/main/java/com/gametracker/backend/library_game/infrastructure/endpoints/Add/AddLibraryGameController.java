package com.gametracker.backend.library_game.infrastructure.endpoints.Add;

import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameCommand;
import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameUseCase;
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
  public ResponseEntity<HttpStatus> execute(
      @RequestBody AddLibraryGameRequest request, Principal principal) {
    AddLibraryGameCommand command =
        new AddLibraryGameCommand(
            request.id(), request.title(), request.rating(), request.status(), principal.getName());
    addLibraryGameUseCase.execute(command);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
