package com.project.jagoga.user.presentation.dto.response;

import com.project.jagoga.user.domain.User;

public class UserResponseDto {

    private final String email;
    private final String name;
    private final String phone;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public static UserResponseDto createInstance(User user) {
        return new UserResponseDto(user.getEmail(), user.getName(), user.getPhone());
    }

    private UserResponseDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}