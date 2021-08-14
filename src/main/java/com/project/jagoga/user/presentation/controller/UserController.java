package com.project.jagoga.user.presentation;

import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.response.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userUseCase;

    public UserController(UserService userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto signUp(@RequestBody final UserCreateRequestDto userCreateRequestDto) {
        User user = userUseCase.signUp(userCreateRequestDto.toEntity());
        return UserResponseDto.createInstance(user);
    }

}
