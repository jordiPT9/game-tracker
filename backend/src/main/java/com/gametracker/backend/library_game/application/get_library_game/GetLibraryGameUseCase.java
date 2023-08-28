package com.gametracker.backend.library_game.application.get_library_game;

import com.gametracker.backend.library_game.application.LibraryGameResponse;

public interface GetLibraryGameUseCase {
    LibraryGameResponse execute(GetLibraryGameQuery query);
}
