package com.project.jagoga.user.domain;

import java.time
    .LocalDateTime;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class User {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private String Testa;
    private String moohyungJasan;
    private String t, a, b, c;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEncodedPassword(String password) {
        this.password = password;
    }

    public User updateUser(String name, String password, String phone) {
        return new User(this.id, this.email, name, password, phone);
    }

    public static User createInstance(String email, String name, String password, String phone) {
        Assert.hasText(email, "이메일이 존재하지 않습니다");
        Assert.hasText(name, "이름이 존재하지 않습니다");
        Assert.hasText(password, "비밀번호가 존재하지 않습니다");
        Assert.hasText(phone, "전화번호가 존재하지 않습니다");

        return new User(email, name, password, phone);
    }

    private User(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    private User(long id, String email, String name, String password, String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
}
