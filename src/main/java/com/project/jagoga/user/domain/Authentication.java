package com.project.jagoga.user.domain;

import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;

public interface Authentication {

    public String login(LoginRequestDto loginRequestDto);

    public void verifyLogin(String token);
}
