package com.project.jagoga.roomtype.presentation.controller;

import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.exception.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.project.jagoga.roomtype.presentation.controller"})
public class RoomTypeExceptionHandler {

    @ExceptionHandler(NotExistAccommodationException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistAccommodationException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
    }
}
