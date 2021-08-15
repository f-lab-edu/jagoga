package com.project.jagoga.exception.products;

public class DuplicatedProductException extends RuntimeException {

    public DuplicatedProductException() {
        super("이미 존재하는 상품입니다.");
    }
}
