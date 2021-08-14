package com.project.jagoga.domain.user.application;

import com.project.jagoga.domain.user.dto.UserCreateRequestDto;
import com.project.jagoga.domain.user.dto.UserResponse;

public interface UserUseCase {

    public UserResponse signUp(UserCreateRequestDto userCreateRequestDto);
}
