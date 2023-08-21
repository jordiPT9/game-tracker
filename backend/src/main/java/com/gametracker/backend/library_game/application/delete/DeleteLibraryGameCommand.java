package com.gametracker.backend.library_game.application.delete;

public record DeleteLibraryGameCommand(
        String id,
        String username) {
}
