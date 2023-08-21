package com.gametracker.backend.library_game.application.add;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameDoesNotExistException;
import com.gametracker.backend.game.domain.GameService;
import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameAlreadyAddedException;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class AddLibraryGameUseCaseImpl implements AddLibraryGameUseCase {
    private final LibraryGameRepository libraryGameRepository;
    private final GameService gameService;

    public AddLibraryGameUseCaseImpl(LibraryGameRepository libraryGameRepository, GameService gameService) {
        this.libraryGameRepository = libraryGameRepository;
        this.gameService = gameService;
    }

    @Override
    public void execute(AddLibraryGameCommand command) {
        Game game = gameService.getGame(command.title());

        if (game == null) {
            throw new GameDoesNotExistException(command.title());
        }

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