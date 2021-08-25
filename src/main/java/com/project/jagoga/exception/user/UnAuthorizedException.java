package com.project.jagoga.exception.user;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        super("인증되지 않은 사용자입니다");
    }
}
