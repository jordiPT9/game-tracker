package com.gametracker.backend.game.domain;

public class Game {
    private final String title;
    private final int follows;
    private final String releaseDate;

    public Game(String title, int follows, String releaseDate) {
        this.title = title;
        this.follows = follows;
        this.releaseDate = releaseDate;
    }
}
