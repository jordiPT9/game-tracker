package com.gametracker.backend.libraryGame.application.Get;

public interface GetLibraryGameUseCase {
    LibraryGameResponse execute(GetLibraryGameQuery query);
}
