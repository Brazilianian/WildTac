package com.wildtac.handler;

import com.wildtac.exception.alreadyexists.EntityAlreadyExistsException;
import com.wildtac.exception.wasnotfound.EntityWasNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class EntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EntityAlreadyExistsException> catchEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<EntityWasNotFoundException> catchEntityWasNotFoundException(EntityWasNotFoundException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }
}
