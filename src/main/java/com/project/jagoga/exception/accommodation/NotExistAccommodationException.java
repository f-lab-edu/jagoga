package com.project.jagoga.exception.accommodation;

public class NotExistAccommodationException extends RuntimeException {

    public NotExistAccommodationException() {
        super("존재하지 않는 숙소입니다.");
    }
}
