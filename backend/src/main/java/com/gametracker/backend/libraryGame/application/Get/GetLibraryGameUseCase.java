package com.gametracker.backend.libraryGame.application.Get;

public interface GetLibraryGameUseCase {
    LibraryGameDTO execute(GetLibraryGameQuery query);
}
