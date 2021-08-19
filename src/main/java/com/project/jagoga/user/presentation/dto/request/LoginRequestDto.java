package com.project.jagoga.user.presentation.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    // TODO : validation
    private String email;
    private String password;
}
