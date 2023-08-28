package com.gametracker.backend.library_game.application.get_library_games;

import com.gametracker.backend.library_game.application.LibraryGameResponse;

import java.util.List;

public interface GetLibraryGamesUseCase {
    List<LibraryGameResponse> execute(GetLibraryGamesQuery command);
}
