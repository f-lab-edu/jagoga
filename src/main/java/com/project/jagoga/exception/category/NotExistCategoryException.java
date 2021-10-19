package com.project.jagoga.exception.category;

public class NotExistCategoryException extends RuntimeException {

    public NotExistCategoryException() {
        super("존재하지 않는 카테고리입니다.");
    }
}
