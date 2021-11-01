package com.project.jagoga.roominventory.presentation.controller;

import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.exception.roominventory.DuplicatedInventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.project.jagoga.roominventory.presentation.controller"})
public class RoomInventoryExceptionHandler {

    @ExceptionHandler(DuplicatedInventoryException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicatedInventoryException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.createError(exception.getMessage()));
    }
}
