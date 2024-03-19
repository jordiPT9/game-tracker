package com.gametracker.backend.library_game.application.delete_library_game;

import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameAccessDeniedException;
import com.gametracker.backend.library_game.domain.LibraryGameNotFoundException;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteLibraryGameUseCase {
  private final LibraryGameRepository libraryGameRepository;

  public DeleteLibraryGameUseCase(LibraryGameRepository libraryGameRepository) {
    this.libraryGameRepository = libraryGameRepository;
  }

  public void execute(DeleteLibraryGameCommand command) {
    LibraryGame libraryGame = libraryGameRepository.findById(command.id());

    if (libraryGame == null) {
      throw new LibraryGameNotFoundException(command.id());
    }

    if (!libraryGame.getUsername().equals(command.username())) {
      throw new LibraryGameAccessDeniedException(command.id());
    }

    libraryGameRepository.deleteById(command.id());
  }
}
