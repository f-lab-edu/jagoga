package com.project.jagoga.user.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class AuthUser {

    private Long id;
    private String email;
    private Role role;

    public static AuthUser createInstance(long id, String email, Role role) {
        Assert.isTrue(id > 0, "id가 존재하지 않습니다");
        Assert.hasText(email, "이메일이 존재하지 않습니다");
        Assert.notNull(role, "권한이 존재하지 않습니다");

        return new AuthUser(id, email, role);
    }

    private AuthUser(long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
