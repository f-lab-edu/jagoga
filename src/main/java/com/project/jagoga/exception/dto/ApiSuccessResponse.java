package com.project.jagoga.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiSuccessResponse<T> {

    private final String status = "success";
    private T data;

    public static <T> ApiSuccessResponse createInstance(T data) {
        return new ApiSuccessResponse(data);
    }

    private ApiSuccessResponse(T data) {
        this.data = data;
    }
}
