package com.project.jagoga.user.domain;

public enum Role {

    BASIC("일반 사용자"), OWNER("사장님"), ADMIN("관리자");

    private final String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}