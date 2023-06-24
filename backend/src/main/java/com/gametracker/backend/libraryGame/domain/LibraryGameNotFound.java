package com.gametracker.backend.libraryGame.domain;

public class LibraryGameNotFound extends RuntimeException {
    public LibraryGameNotFound(String id) {
        super("Library game not found with id '" + id + "'");
    }
}
