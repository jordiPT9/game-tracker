package com.gametracker.backend.libraryGame.domain;

public class LibraryGameAccessDeniedException extends RuntimeException {
    public LibraryGameAccessDeniedException(String id) {
        super("You don't have access to library game with id '" + id + "'");
    }
}
