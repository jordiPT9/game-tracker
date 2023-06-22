package com.gametracker.backend.libraryGame.domain;

public class LibraryGameAlreadyAdded extends RuntimeException {
    public LibraryGameAlreadyAdded(String title) {
        super("Library game with title '" + title + "' already added");
    }
}
