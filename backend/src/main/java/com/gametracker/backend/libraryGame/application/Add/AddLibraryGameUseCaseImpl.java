package com.gametracker.backend.libraryGame.application.Add;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameService;
import com.gametracker.backend.game.domain.GameDoesNotExist;
import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameAlreadyAdded;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class AddLibraryGameUseCaseImpl implements AddLibraryGameUseCase {
    private final LibraryGameRepository libraryGameRepository;
    private final GameService igdbService;

    public AddLibraryGameUseCaseImpl(LibraryGameRepository libraryGameRepository, GameService igdbService) {
        this.libraryGameRepository = libraryGameRepository;
        this.igdbService = igdbService;
    }

    @Override
    public void execute(AddLibraryGameCommand command) {
        Game game = igdbService.searchGame(command.title());

        if (game == null) {
            throw new GameDoesNotExist(command.title());
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
            throw new LibraryGameAlreadyAdded(l.getTitle());
        }

        libraryGameRepository.save(libraryGame);
    }
}
