package com.project.jagoga.global.error;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}