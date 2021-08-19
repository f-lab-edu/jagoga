package com.project.jagoga.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    private final String status = "error";
    private String message;

    public static ApiErrorResponse createInstance(String message) {
        return new ApiErrorResponse(message);
    }

    private ApiErrorResponse(String message) {
        this.message = message;
    }
}
