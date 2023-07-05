package com.gametracker.backend.libraryGame.application.Get;

import java.util.List;

public interface GetLibraryGamesUseCase {
    List<LibraryGameResponse> execute(GetLibraryGamesQuery command);
}
