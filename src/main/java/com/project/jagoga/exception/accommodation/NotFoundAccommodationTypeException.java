package com.project.jagoga.exception.accommodation;

public class NotFoundAccommodationTypeException extends RuntimeException {

    public NotFoundAccommodationTypeException() {
        super("존재하지 않는 숙소 타입 입니다.");
    }
}
