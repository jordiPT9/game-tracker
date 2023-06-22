package com.gametracker.backend.libraryGame.application.AddLibraryGame;

import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameAlreadyAdded;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class AddLibraryGameUseCaseImpl implements AddLibraryGameUseCase {
    private final LibraryGameRepository libraryGameRepository;

    public AddLibraryGameUseCaseImpl(LibraryGameRepository libraryGameRepository) {
        this.libraryGameRepository = libraryGameRepository;
    }

    @Override
    public void execute(AddLibraryGameCommand command) {
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
