package com.project.jagoga.user.presentation.dto.response;

import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String email;
    private final String name;
    private final String phone;
    private final String role;

    public static UserResponseDto createInstance(User user) {
        return new UserResponseDto(user.getEmail(), user.getName(), user.getPhone(), user.getRole());
    }

    private UserResponseDto(String email, String name, String phone, Role role) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role.getTitle();
    }
}