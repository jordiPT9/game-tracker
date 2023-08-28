package com.gametracker.backend.library_game.application.add_library_game;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameDoesNotExistException;
import com.gametracker.backend.game.domain.GameRepository;
import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameAlreadyAddedException;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class AddLibraryGameUseCaseImpl implements AddLibraryGameUseCase {
    private final LibraryGameRepository libraryGameRepository;
    private final GameRepository gameRepository;

    public AddLibraryGameUseCaseImpl(LibraryGameRepository libraryGameRepository, GameRepository gameRepository) {
        this.libraryGameRepository = libraryGameRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void execute(AddLibraryGameCommand command) {
        gameRepository.findGame(command.title())
                .orElseThrow(() -> new GameDoesNotExistException(command.title()));

        LibraryGame newLibraryGame = LibraryGame.builder()
                .id(command.id())
                .title(command.title())
                .rating(command.rating())
                .status(command.status())
                .username(command.username())
                .build();

        LibraryGame libraryGame = libraryGameRepository.findByTitleAndUsername(newLibraryGame.getTitle(), newLibraryGame.getUsername());

        if (libraryGame != null) {
            throw new LibraryGameAlreadyAddedException(libraryGame.getTitle());
        }

        libraryGameRepository.save(newLibraryGame);
    }
}
