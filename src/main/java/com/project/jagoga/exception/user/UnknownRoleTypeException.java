package com.project.jagoga.exception.user;

public class UnknownRoleTypeException extends RuntimeException {

    public UnknownRoleTypeException() {
        super("알 수 없는 권한입니다");
    }
}
