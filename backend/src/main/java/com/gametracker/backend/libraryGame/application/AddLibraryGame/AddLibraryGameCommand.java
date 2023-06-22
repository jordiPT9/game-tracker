package com.gametracker.backend.libraryGame.application.AddLibraryGame;

import com.gametracker.backend.libraryGame.domain.GameStatus;

public record AddLibraryGameCommand(
        String id,
        String title,
        double rating,
        GameStatus status,
        String username
) {
}
