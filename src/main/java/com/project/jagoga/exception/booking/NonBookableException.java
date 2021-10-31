package com.project.jagoga.exception.booking;

public class NonBookableException extends RuntimeException {

    public NonBookableException() {
        super("예약이 불가능합니다.");
    }
}
