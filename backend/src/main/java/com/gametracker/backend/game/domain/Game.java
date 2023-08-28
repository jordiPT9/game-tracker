package com.gametracker.backend.game.domain;

import java.util.Objects;

public class Game {
    private String title;
    private int follows;
    private String releaseDate;

    private Game() {
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public String getTitle() {
        return title;
    }

    public int getFollows() {
        return follows;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setFollows(int follows) {
        this.follows = follows;
    }

    private void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", follows=" + follows +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return follows == game.follows && Objects.equals(title, game.title) && Objects.equals(releaseDate, game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, follows, releaseDate);
    }

    public static class GameBuilder {
        private final Game game = new Game();

        private GameBuilder() {
        }

        public GameBuilder title(String title) {
            game.setTitle(title);
            return this;
        }

        public GameBuilder follows(int follows) {
            game.setFollows(follows);
            return this;
        }

        public GameBuilder releaseDate(String releaseDate) {
            game.setReleaseDate(releaseDate);
            return this;
        }

        public Game build() {
            return game;
        }
    }
}
