package com.gametracker.backend.libraryGame.application.Add;

import com.gametracker.backend.libraryGame.domain.LibraryGameStatus;

public record AddLibraryGameCommand(
        String id,
        String title,
        double rating,
        LibraryGameStatus status,
        String username
) {
}
