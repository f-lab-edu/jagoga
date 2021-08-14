package com.project.jagoga.user.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice({})
public class UserExceptionHandler {
}

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(bindingResult));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}