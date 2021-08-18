package com.project.jagoga.exception.user;

public class DuplicatedUserException extends RuntimeException {

    public DuplicatedUserException() {
        super("이미 존재하는 회원입니다");
    }
}
