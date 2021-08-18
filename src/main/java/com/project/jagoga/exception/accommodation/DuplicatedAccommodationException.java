package com.project.jagoga.exception.accommodation;

public class DuplicatedAccommodationException extends RuntimeException {

    public DuplicatedAccommodationException() {
        super("이미 존재하는 상품입니다.");
    }
}
