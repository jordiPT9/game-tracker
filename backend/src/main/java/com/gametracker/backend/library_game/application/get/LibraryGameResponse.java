package com.gametracker.backend.library_game.application.get;

public record LibraryGameResponse(
        String title,
        String id,
        String status,
        double rating
) {
}
