package com.gametracker.backend.library_game.application.update_library_game;

import com.gametracker.backend.library_game.domain.LibraryGameStatus;

public record UpdateLibraryGameRequest(
    String libraryGameId, double rating, LibraryGameStatus status) {}
