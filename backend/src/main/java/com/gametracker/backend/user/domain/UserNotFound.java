package com.gametracker.backend.user.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String username) {
        super("User not found with username '" + username + "'");
    }
}
