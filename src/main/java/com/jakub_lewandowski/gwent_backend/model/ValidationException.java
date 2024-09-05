package com.jakub_lewandowski.gwent_backend.model;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
