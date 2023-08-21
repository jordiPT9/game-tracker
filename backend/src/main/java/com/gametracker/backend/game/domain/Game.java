package com.gametracker.backend.game.domain;

public record Game(
        String title,
        int follows,
        String releaseDate
) {
}