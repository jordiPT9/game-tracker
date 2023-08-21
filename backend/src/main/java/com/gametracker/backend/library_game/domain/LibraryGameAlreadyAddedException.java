package com.gametracker.backend.library_game.domain;

public class LibraryGameAlreadyAddedException extends RuntimeException {
    public LibraryGameAlreadyAddedException(String title) {
        super("Library game with title '" + title + "' is already added");
    }
}
