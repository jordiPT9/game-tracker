package com.gametracker.backend.library_game.application.get;

import java.util.List;

public interface GetLibraryGamesUseCase {
    List<LibraryGameResponse> execute(GetLibraryGamesQuery command);
}
