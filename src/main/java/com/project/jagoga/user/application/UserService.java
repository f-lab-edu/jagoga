package com.project.jagoga.user.application;

import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserUpdateRequestDto;

public interface UserService {

    public User signUp(UserCreateRequestDto userCreateRequestDto);

    public User updateUser(long id, UserUpdateRequestDto userUpdateRequestDto, AuthUser loginUser);

    public User changeRoleToOwner(long id);
}
