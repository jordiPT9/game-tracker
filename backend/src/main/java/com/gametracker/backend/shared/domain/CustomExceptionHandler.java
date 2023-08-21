package com.gametracker.backend.shared.domain;

import com.gametracker.backend.auth.domain.UnauthorizedException;
import com.gametracker.backend.game.domain.GameDoesNotExistException;
import com.gametracker.backend.library_game.domain.LibraryGameAccessDeniedException;
import com.gametracker.backend.library_game.domain.LibraryGameAlreadyAddedException;
import com.gametracker.backend.library_game.domain.LibraryGameNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {GameDoesNotExistException.class})
    protected ResponseEntity<Object> handleGameDoesNotExistException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {LibraryGameAlreadyAddedException.class})
    protected ResponseEntity<Object> handleLibraryGameAlreadyAddedException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {LibraryGameNotFoundException.class})
    protected ResponseEntity<Object> handleLibraryGameNotFoundException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {LibraryGameAccessDeniedException.class})
    protected ResponseEntity<Object> handleLibraryGameAccessDeniedException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
