package com.project.jagoga.user.application;

import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;

public interface UserService {

    public User signUp(UserCreateRequestDto user);
}
