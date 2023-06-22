package com.gametracker.backend.libraryGame.infrastructure.entrypoints.AddLibraryGame;

import com.gametracker.backend.libraryGame.domain.GameStatus;

public record AddLibraryGameRequest(
        String id,
        String title,
        double rating,
        GameStatus status
) {
}
