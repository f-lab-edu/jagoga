package com.project.jagoga.user.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
public class User {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEncodedPassword(String password) {
        this.password = password;
    }

    public User updateUser(String name, String password, String phone) {
        return new User(this.id, this.email, name, password, phone, this.role);
    }

    public static User createInstance(String email, String name, String password, String phone) {
        Assert.hasText(email, "이메일이 존재하지 않습니다");
        Assert.hasText(name, "이름이 존재하지 않습니다");
        Assert.hasText(password, "비밀번호가 존재하지 않습니다");
        Assert.hasText(phone, "전화번호가 존재하지 않습니다");

        return new User(email, name, password, phone, getUserDefaultRole());
    }

    private static Role getUserDefaultRole() {
        return Role.BASIC;
    }

    private User(String email, String name, String password, String phone, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    private User(long id, String email, String name, String password, String phone, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }
}
