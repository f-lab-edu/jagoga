package com.project.jagoga.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.jagoga.exception.user.UnknownRoleTypeException;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

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