package com.gametracker.backend.library_game.application.get;

import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetLibraryGamesUseCaseImpl implements GetLibraryGamesUseCase {
    private final LibraryGameRepository libraryGameRepository;

    public GetLibraryGamesUseCaseImpl(LibraryGameRepository libraryGameRepository) {
        this.libraryGameRepository = libraryGameRepository;
    }

    @Override
    public List<LibraryGameResponse> execute(GetLibraryGamesQuery command) {
        List<LibraryGame> libraryGames = libraryGameRepository.findByUsername(command.username());

        return libraryGames
                .stream()
                .map(libraryGame -> new LibraryGameResponse(
                        libraryGame.getId(),
                        libraryGame.getTitle(),
                        libraryGame.getStatus().name(),
                        libraryGame.getRating()
                ))
                .collect(Collectors.toList());
    }
}
