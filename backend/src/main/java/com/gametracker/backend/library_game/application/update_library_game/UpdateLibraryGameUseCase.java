package com.gametracker.backend.library_game.application.update_library_game;

import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameNotFoundException;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateLibraryGameUseCase {
  private final LibraryGameRepository libraryGameRepository;

  public UpdateLibraryGameUseCase(LibraryGameRepository libraryGameRepository) {
    this.libraryGameRepository = libraryGameRepository;
  }

  public void execute(UpdateLibraryGameRequest request) {
    LibraryGame libraryGame = libraryGameRepository.findById(request.libraryGameId());

    if (libraryGame == null) {
      throw new LibraryGameNotFoundException(request.libraryGameId());
    }

    libraryGame.setRating(request.rating());
    libraryGame.setStatus(request.status());
    libraryGameRepository.save(libraryGame);
  }
}
