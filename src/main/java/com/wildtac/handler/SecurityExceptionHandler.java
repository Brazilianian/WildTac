package com.wildtac.handler;

import com.wildtac.exception.InvalidJwtTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class SecurityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExpiredJwtException> catchExpiredJwtException(ExpiredJwtException e) {
        return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InvalidJwtTokenException> catchInvalidJwtTokenException(InvalidJwtTokenException e) {
        return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<UsernameNotFoundException> catchUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }
}
