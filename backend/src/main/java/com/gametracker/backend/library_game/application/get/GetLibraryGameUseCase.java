package com.gametracker.backend.library_game.application.get;

public interface GetLibraryGameUseCase {
    LibraryGameResponse execute(GetLibraryGameQuery query);
}
