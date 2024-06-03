package com.gametracker.backend.library_game.infrastructure.endpoints.Add;

import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameRequest;
import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameUseCase;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddLibraryGameController {
  private final AddLibraryGameUseCase addLibraryGameUseCase;

  public AddLibraryGameController(AddLibraryGameUseCase addLibraryGameUseCase) {
    this.addLibraryGameUseCase = addLibraryGameUseCase;
  }

  @PostMapping("/library-games")
  public ResponseEntity<HttpStatus> execute(
          @RequestBody AddLibraryGameRequestBody requestBody, Principal principal) {
    AddLibraryGameRequest request =
        new AddLibraryGameRequest(
            requestBody.id(), requestBody.title(), requestBody.rating(), requestBody.status(), principal.getName());
    addLibraryGameUseCase.execute(request);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
