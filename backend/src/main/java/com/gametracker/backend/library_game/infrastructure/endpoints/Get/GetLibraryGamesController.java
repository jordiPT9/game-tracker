package com.gametracker.backend.library_game.infrastructure.endpoints.Get;

import com.gametracker.backend.library_game.application.LibraryGameResponse;
import com.gametracker.backend.library_game.application.get_library_games.GetLibraryGamesRequest;
import com.gametracker.backend.library_game.application.get_library_games.GetLibraryGamesUseCase;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GetLibraryGamesController {
  private final GetLibraryGamesUseCase getLibraryGamesUseCase;

  public GetLibraryGamesController(GetLibraryGamesUseCase getLibraryGamesUseCase) {
    this.getLibraryGamesUseCase = getLibraryGamesUseCase;
  }

  @GetMapping("/library-games")
  public ResponseEntity<List<LibraryGameResponse>> execute(Principal principal) {
    GetLibraryGamesRequest request = new GetLibraryGamesRequest(principal.getName());
    List<LibraryGameResponse> libraryGameResponses = getLibraryGamesUseCase.execute(request);

    return new ResponseEntity<>(libraryGameResponses, HttpStatus.OK);
  }
}
