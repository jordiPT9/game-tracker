package com.gametracker.backend.library_game.infrastructure.endpoints.Get;

import com.gametracker.backend.library_game.application.LibraryGameResponse;
import com.gametracker.backend.library_game.application.get_library_game.GetLibraryGameRequest;
import com.gametracker.backend.library_game.application.get_library_game.GetLibraryGameUseCase;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GetLibraryGameController {
  private final GetLibraryGameUseCase getLibraryGameUseCase;

  public GetLibraryGameController(GetLibraryGameUseCase getLibraryGameUseCase) {
    this.getLibraryGameUseCase = getLibraryGameUseCase;
  }

  @GetMapping("/library-games/{libraryGameId}")
  public ResponseEntity<LibraryGameResponse> execute(
      @PathVariable String libraryGameId, Principal principal) {
    GetLibraryGameRequest request = new GetLibraryGameRequest(libraryGameId, principal.getName());
    LibraryGameResponse libraryGameResponse = getLibraryGameUseCase.execute(request);

    return new ResponseEntity<>(libraryGameResponse, HttpStatus.OK);
  }
}
