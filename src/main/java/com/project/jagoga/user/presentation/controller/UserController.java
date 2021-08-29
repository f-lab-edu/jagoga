package com.project.jagoga.user.presentation.controller;

import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserUpdateRequestDto;
import com.project.jagoga.user.presentation.dto.response.JwtResponseDto;
import com.project.jagoga.user.presentation.dto.response.UserResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final Authentication authentication;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponseDto> signUp(@Valid @RequestBody final UserCreateRequestDto userCreateRequestDto) {
        User user = userService.signUp(userCreateRequestDto);
        return ApiResponse.createSuccess(UserResponseDto.createInstance(user));
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponseDto> login(@Valid @RequestBody final LoginRequestDto loginRequestDto) {
        String token = authentication.login(loginRequestDto);
        return ApiResponse.createSuccess(JwtResponseDto.createInstance(token));
    }

    @LoginCheck
    @PutMapping("/{id}")
    public ApiResponse<UserResponseDto> updateUser(
        @PathVariable("id") final long id,
        @Valid @RequestBody final UserUpdateRequestDto userUpdateRequestDto
    ) {
        User user = userService.updateUser(id, userUpdateRequestDto);
        return ApiResponse.createSuccess(UserResponseDto.createInstance(user));
    }
}
