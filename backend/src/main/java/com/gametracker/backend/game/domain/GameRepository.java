package com.gametracker.backend.game.domain;

import java.util.Optional;

public interface GameRepository {
    Optional<Game> findGame(String title);
}
