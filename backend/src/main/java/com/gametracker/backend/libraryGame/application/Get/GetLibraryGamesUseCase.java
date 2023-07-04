package com.gametracker.backend.libraryGame.application.Get;

import java.util.List;

public interface GetLibraryGamesUseCase {
    List<LibraryGameDTO> execute(GetLibraryGamesQuery command);
}
