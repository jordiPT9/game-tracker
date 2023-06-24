package com.gametracker.backend.libraryGame.application.Delete;

import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameAccessDenied;
import com.gametracker.backend.libraryGame.domain.LibraryGameNotFound;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteLibraryGameUseCaseImpl implements DeleteLibraryGameUseCase {
    private final LibraryGameRepository libraryGameRepository;

    public DeleteLibraryGameUseCaseImpl(LibraryGameRepository libraryGameRepository) {
        this.libraryGameRepository = libraryGameRepository;
    }

    @Override
    public void execute(DeleteLibraryGameCommand command) {
        LibraryGame libraryGame = libraryGameRepository.findById(command.id());

        if (libraryGame == null) {
            throw new LibraryGameNotFound(command.id());
        }

        if (!libraryGame.getUsername().equals(command.username())) {
            throw new LibraryGameAccessDenied(command.id());
        }

        libraryGameRepository.deleteById(command.id());
    }
}
