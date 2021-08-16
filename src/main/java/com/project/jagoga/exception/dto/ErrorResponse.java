package com.project.jagoga.exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    public String message;

    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }

    private ErrorResponse(String body) {
        this.message = body;
    }
}
