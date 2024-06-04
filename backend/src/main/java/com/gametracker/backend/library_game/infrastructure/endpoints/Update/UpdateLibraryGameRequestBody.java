package com.gametracker.backend.library_game.infrastructure.endpoints.Update;

import com.gametracker.backend.library_game.domain.LibraryGameStatus;

public record UpdateLibraryGameRequestBody(double rating, LibraryGameStatus status) {}
