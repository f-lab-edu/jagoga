package com.project.jagoga.user.domain;

import org.springframework.util.Assert;

import java.sql.Timestamp;

public class User {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private Timestamp createdAt;
    private Timestamp UpdatedAt;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
