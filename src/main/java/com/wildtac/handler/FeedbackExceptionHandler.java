package com.wildtac.handler;

import com.wildtac.exception.FeedbackWasNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class FeedbackExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> catchFeedbackWasNotFoundException(FeedbackWasNotFoundException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
