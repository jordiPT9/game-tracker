package com.gametracker.backend.library_game.application.get_library_game;

import com.gametracker.backend.library_game.application.LibraryGameResponse;
import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameAccessDeniedException;
import com.gametracker.backend.library_game.domain.LibraryGameNotFoundException;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class GetLibraryGameUseCaseImpl implements GetLibraryGameUseCase {
    private final LibraryGameRepository libraryGameRepository;

    public GetLibraryGameUseCaseImpl(LibraryGameRepository libraryGameRepository) {
        this.libraryGameRepository = libraryGameRepository;
    }

    @Override
    public LibraryGameResponse execute(GetLibraryGameQuery query) {
        LibraryGame libraryGame = libraryGameRepository.findById(query.id());

        if (libraryGame == null) {
            throw new LibraryGameNotFoundException(query.id());
        }

        if (!libraryGame.getUsername().equals(query.username())) {
            throw new LibraryGameAccessDeniedException(query.id());
        }

        return new LibraryGameResponse(
                libraryGame.getTitle(),
                libraryGame.getId(),
                libraryGame.getStatus().name(),
                libraryGame.getRating()
        );
    }
}
