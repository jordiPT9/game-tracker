package com.gametracker.backend.library_game.infrastructure.entrypoints.Get;

import com.gametracker.backend.library_game.application.get.GetLibraryGamesQuery;
import com.gametracker.backend.library_game.application.get.GetLibraryGamesUseCase;
import com.gametracker.backend.library_game.application.get.LibraryGameResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GetLibraryGamesController {
    private final GetLibraryGamesUseCase getLibraryGamesUseCase;

    public GetLibraryGamesController(GetLibraryGamesUseCase getLibraryGamesUseCase) {
        this.getLibraryGamesUseCase = getLibraryGamesUseCase;
    }

    @GetMapping("/library-games")
    public ResponseEntity<List<LibraryGameResponse>> execute(Principal principal) {
        GetLibraryGamesQuery query = new GetLibraryGamesQuery(principal.getName());
        List<LibraryGameResponse> libraryGameResponses = getLibraryGamesUseCase.execute(query);

        return new ResponseEntity<>(libraryGameResponses, HttpStatus.OK);
    }
}