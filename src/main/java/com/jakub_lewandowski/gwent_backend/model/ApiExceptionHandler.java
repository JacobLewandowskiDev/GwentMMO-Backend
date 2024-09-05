package com.jakub_lewandowski.gwent_backend.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPlayerException(ValidationException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.fromException(e));
    }
}
