package com.wildtac.exception;

import java.util.Map;

public class ValidationException extends RuntimeException{
    private Map<String, String> errors;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
