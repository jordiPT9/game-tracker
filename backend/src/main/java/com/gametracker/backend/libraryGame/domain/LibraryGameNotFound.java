package com.gametracker.backend.libraryGame.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LibraryGameNotFound extends RuntimeException {
    public LibraryGameNotFound(String id) {
        super("Library game not found with id '" + id + "'");
    }
}
