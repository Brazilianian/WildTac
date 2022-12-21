package com.wildtac.exception;

import io.jsonwebtoken.JwtException;

public class InvalidJwtTokenException extends JwtException {

    public InvalidJwtTokenException(String message) {
        super(message);
    }

    public InvalidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
