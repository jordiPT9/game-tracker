package com.gametracker.backend.game.domain;

public class GameDoesNotExistException extends RuntimeException {
  public GameDoesNotExistException(String title) {
    super("Game with title '" + title + "' doesn't exist");
  }
}
