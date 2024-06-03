package com.gametracker.backend.library_game.infrastructure.endpoints.Add;

import com.gametracker.backend.library_game.domain.LibraryGameStatus;

public record AddLibraryGameRequestBody(
    String id, String title, double rating, LibraryGameStatus status) {}
