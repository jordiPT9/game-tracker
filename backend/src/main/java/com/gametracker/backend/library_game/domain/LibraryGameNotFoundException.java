package com.gametracker.backend.library_game.domain;

public class LibraryGameNotFoundException extends RuntimeException {
  public LibraryGameNotFoundException(String id) {
    super("Library game not found with id '" + id + "'");
  }
}
