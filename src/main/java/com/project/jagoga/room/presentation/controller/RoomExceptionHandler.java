package com.project.jagoga.room.presentation.controller;

import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.exception.room.NotExistRoomException;
import com.project.jagoga.exception.roomtype.NotExistRoomTypeException;
import com.project.jagoga.exception.user.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.project.jagoga.room.presentation.controller"})
public class RoomExceptionHandler {

    @ExceptionHandler(NotExistAccommodationException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistAccommodationException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(NotExistRoomTypeException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistRoomTypeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(NotExistRoomException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistRoomException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<?>> handleForbiddenException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }
}
