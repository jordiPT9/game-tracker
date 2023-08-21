package com.gametracker.backend.library_game.infrastructure.entrypoints.Get;

import com.gametracker.backend.library_game.application.get.GetLibraryGameQuery;
import com.gametracker.backend.library_game.application.get.GetLibraryGameUseCase;
import com.gametracker.backend.library_game.application.get.LibraryGameResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class GetLibraryGameController {
    private final GetLibraryGameUseCase getLibraryGameUseCase;

    public GetLibraryGameController(GetLibraryGameUseCase getLibraryGameUseCase) {
        this.getLibraryGameUseCase = getLibraryGameUseCase;
    }

    @GetMapping("/library-games/{libraryGameId}")
    public ResponseEntity<LibraryGameResponse> execute(@PathVariable String libraryGameId, Principal principal) {
        GetLibraryGameQuery query = new GetLibraryGameQuery(libraryGameId, principal.getName());
        LibraryGameResponse libraryGameResponse = getLibraryGameUseCase.execute(query);

        return new ResponseEntity<>(libraryGameResponse, HttpStatus.OK);
    }
}
