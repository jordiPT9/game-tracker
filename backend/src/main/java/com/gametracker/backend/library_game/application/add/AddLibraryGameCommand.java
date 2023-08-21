package com.gametracker.backend.library_game.application.add;

import com.gametracker.backend.library_game.domain.LibraryGameStatus;

public record AddLibraryGameCommand(
        String id,
        String title,
        double rating,
        LibraryGameStatus status,
        String username
) {
}
