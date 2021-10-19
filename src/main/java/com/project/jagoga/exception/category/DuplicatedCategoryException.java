package com.project.jagoga.exception.category;

public class DuplicatedCategoryException extends RuntimeException {

    public DuplicatedCategoryException() {
        super("이미 존재하는 카테고리입니다.");
    }
}
