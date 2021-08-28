package com.project.jagoga.user.domain;

public enum Role {

    BASIC("일반 사용자"), OWNER("사장님"), ADMIN("관리자");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}