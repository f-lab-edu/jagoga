package com.project.jagoga.exception.user;

public class UserAuthenticationFailException extends RuntimeException {

    public UserAuthenticationFailException() {
        super("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다");
    }
}
