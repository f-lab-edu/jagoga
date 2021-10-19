package com.project.jagoga.category.presentation.controller;

import com.project.jagoga.exception.category.DuplicatedCategoryException;
import com.project.jagoga.exception.category.NotExistCategoryException;
import com.project.jagoga.exception.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.project.jagoga.category"})
public class CategoryExceptionHandler {

    @ExceptionHandler(DuplicatedCategoryException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicatedCategoryException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(NotExistCategoryException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistCategoryTypeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
    }
}
