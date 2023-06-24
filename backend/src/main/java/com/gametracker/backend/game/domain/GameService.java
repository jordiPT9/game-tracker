package com.gametracker.backend.game.domain;

import com.gametracker.backend.game.domain.Game;

public interface GameService {
    Game searchGame(String title);
}
