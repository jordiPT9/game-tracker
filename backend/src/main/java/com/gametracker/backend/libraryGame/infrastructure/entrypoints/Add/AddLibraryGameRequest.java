package com.gametracker.backend.libraryGame.infrastructure.entrypoints.Add;

import com.gametracker.backend.libraryGame.domain.LibraryGameStatus;

public record AddLibraryGameRequest(
        String id,
        String title,
        double rating,
        LibraryGameStatus status
) {
}
