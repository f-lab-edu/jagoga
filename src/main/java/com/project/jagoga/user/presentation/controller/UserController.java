package com.project.jagoga.user.presentation.controller;

import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.response.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userUseCase) {
        this.userService = userUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto signUp(@RequestBody final UserCreateRequestDto userCreateRequestDto) {
        User user = userService.signUp(userCreateRequestDto.toEntity());
        return UserResponseDto.createInstance(user);
    }
}
