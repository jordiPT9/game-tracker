package com.gametracker.backend.library_game.infrastructure.endpoints.Update;

import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameRequest;
import com.gametracker.backend.library_game.application.update_library_game.UpdateLibraryGameRequest;
import com.gametracker.backend.library_game.application.update_library_game.UpdateLibraryGameUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UpdateLibraryGameController {
  private final UpdateLibraryGameUseCase updateLibraryGameUseCase;

  public UpdateLibraryGameController(UpdateLibraryGameUseCase updateLibraryGameUseCase) {
    this.updateLibraryGameUseCase = updateLibraryGameUseCase;
  }

  @PutMapping("/library-games/{libraryGameId}")
  public ResponseEntity<HttpStatus> execute(
      @PathVariable String libraryGameId,  @RequestBody UpdateLibraryGameRequestBody requestBody) {
    UpdateLibraryGameRequest request =
        new UpdateLibraryGameRequest(libraryGameId, requestBody.rating(), requestBody.status());
    updateLibraryGameUseCase.execute(request);

      return new ResponseEntity<>(HttpStatus.OK);
  }
}
