package com.gametracker.backend.shared;

import com.gametracker.backend.game.domain.GameDoesNotExist;
import com.gametracker.backend.libraryGame.domain.LibraryGameAccessDenied;
import com.gametracker.backend.libraryGame.domain.LibraryGameAlreadyAdded;
import com.gametracker.backend.libraryGame.domain.LibraryGameNotFound;
import com.gametracker.backend.user.domain.BadCredentials;
import com.gametracker.backend.user.domain.UserNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadCredentials.class})
    protected ResponseEntity<Object> handleBadCredentials(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {UserNotFound.class})
    protected ResponseEntity<Object> handleUserNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {LibraryGameNotFound.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {LibraryGameAlreadyAdded.class})
    protected ResponseEntity<Object> handleLibraryGameAlreadyAdded(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {GameDoesNotExist.class})
    protected ResponseEntity<Object> handleGameDoesNotExist(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(value = {LibraryGameAccessDenied.class})
    protected ResponseEntity<Object> handleLibraryGameAccessDenied(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}