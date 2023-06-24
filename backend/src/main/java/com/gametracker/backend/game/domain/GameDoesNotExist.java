package com.gametracker.backend.game.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GameDoesNotExist extends RuntimeException {
    public GameDoesNotExist(String title) {
        super("Game with title '" + title + "' doesn't exist");
    }
}
