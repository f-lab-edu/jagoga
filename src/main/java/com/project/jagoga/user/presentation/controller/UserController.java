package com.project.jagoga.user.presentation.controller;

import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.response.JwtResponseDto;
import com.project.jagoga.user.presentation.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final Authentication authentication;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponseDto> signUp(@RequestBody final UserCreateRequestDto userCreateRequestDto) {
        User user = userService.signUp(userCreateRequestDto.toEntity());
        return ApiResponse.createSuccess(UserResponseDto.createInstance(user));
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponseDto> login(@RequestBody final LoginRequestDto loginRequestDto) {
        String token = authentication.login(loginRequestDto);
        return ApiResponse.createSuccess(JwtResponseDto.createInstance(token));
    }

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody final LoginRequestDto loginRequestDto) {
        String token = authentication.login(loginRequestDto);
        return JwtResponseDto.createInstance(token);
    }
}
