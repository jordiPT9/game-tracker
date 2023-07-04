package com.gametracker.backend.libraryGame.application.Add;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameDoesNotExistException;
import com.gametracker.backend.game.domain.GameService;
import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameAlreadyAddedException;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
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
        Game game = gameService.searchGame(command.title());

        if (game == null) {
            throw new GameDoesNotExistException(command.title());
        }

        LibraryGame libraryGame = new LibraryGame(
                command.id(),
                command.title(),
                command.rating(),
                command.status(),
                command.username()
        );

        LibraryGame l = libraryGameRepository.findByTitleAndUsername(libraryGame.getTitle(), libraryGame.getUsername());
        if (l != null) {
            throw new LibraryGameAlreadyAddedException(l.getTitle());
        }

        libraryGameRepository.save(libraryGame);
    }
}
