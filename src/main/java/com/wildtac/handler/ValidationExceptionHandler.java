package com.wildtac.handler;

import com.wildtac.dto.ValidationResponseDto;
import com.wildtac.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ValidationResponseDto> catchValidationException(ValidationException e) {
        log.warn(e.getMessage());
        ValidationResponseDto validationResponseDto = new ValidationResponseDto(e.getMessage(), e.getErrors());
        return new ResponseEntity<>(validationResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
