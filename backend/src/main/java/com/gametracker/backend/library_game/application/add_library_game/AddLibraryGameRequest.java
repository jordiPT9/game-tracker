package com.gametracker.backend.library_game.application.add_library_game;

import com.gametracker.backend.library_game.domain.LibraryGameStatus;

public record AddLibraryGameRequest(
    String id, String title, double rating, LibraryGameStatus status, String username) {}
