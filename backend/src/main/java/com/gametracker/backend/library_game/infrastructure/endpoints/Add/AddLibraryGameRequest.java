package com.gametracker.backend.library_game.infrastructure.endpoints.Add;

import com.gametracker.backend.library_game.domain.LibraryGameStatus;

public record AddLibraryGameRequest(
    String id, String title, double rating, LibraryGameStatus status) {}
