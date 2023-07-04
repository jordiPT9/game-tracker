package com.gametracker.backend.libraryGame.infrastructure.entrypoints.Get;

import com.gametracker.backend.libraryGame.application.Get.GetLibraryGameQuery;
import com.gametracker.backend.libraryGame.application.Get.GetLibraryGameUseCase;
import com.gametracker.backend.libraryGame.application.Get.LibraryGameDTO;
import com.gametracker.backend.libraryGame.domain.LibraryGameAccessDeniedException;
import com.gametracker.backend.libraryGame.domain.LibraryGameNotFoundException;
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
    public ResponseEntity<?> execute(@PathVariable String libraryGameId, Principal principal) {
        GetLibraryGameQuery command = new GetLibraryGameQuery(
                libraryGameId,
                principal.getName()
        );

        LibraryGameDTO libraryGameDTO;
        try {
            libraryGameDTO = getLibraryGameUseCase.execute(command);
        } catch (LibraryGameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (LibraryGameAccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(libraryGameDTO, HttpStatus.OK);
    }
}
