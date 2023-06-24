package com.gametracker.backend.libraryGame.domain;

public class LibraryGameAccessDenied extends RuntimeException {
    public LibraryGameAccessDenied(String id) {
        super("You don't have access to library game with id '" + id + "'");
    }
}
