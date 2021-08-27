package com.project.jagoga.user.infrastructure;

import com.project.jagoga.exception.user.UnAuthorizedException;
import com.project.jagoga.user.application.impl.UserServiceImpl;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtTokenAuthenticationTest {

    UserRepository userRepository = new MemoryUserRepository();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder);
    JwtTokenAuthentication jwtTokenAuthentication = new JwtTokenAuthentication("testSecretKey", userRepository, passwordEncoder);

    String email;
    String name;
    String phone;
    String password;
    UserCreateRequestDto userCreateRequestDto;

    @BeforeEach
    public void init() {
        email = "verifyNormalToken@test";
        name = "testname";
        password = "testpassword";
        phone = "010-1234-1234";
        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
    }

    @Test
    @DisplayName("로그인 이후에 발행 된 정상토큰에 대한 검증")
    public void verifyNormalToken() {
        // when
        userService.signUp(userCreateRequestDto);
        String normalToken = jwtTokenAuthentication.login(new LoginRequestDto(email, password));

        // then
        jwtTokenAuthentication.verifyLogin(normalToken);
    }

    @Test
    @DisplayName("비정상 토큰에 대한 검증시 예외발생")
    public void verifyAbnormalToken() {
        // when
        String abnormalToken = "abnormalToken";
        Exception exception = assertThrows(UnAuthorizedException.class,
                () -> jwtTokenAuthentication.verifyLogin(abnormalToken));

        // then
        assertEquals("인증되지 않은 사용자입니다", exception.getMessage());
    }

    @Test
    @DisplayName("null 토큰에 대한 검증시 예외발생")
    public void verifyNullToken() {
        // when
        String nullToken = null;
        Exception exception = assertThrows(UnAuthorizedException.class,
                () -> jwtTokenAuthentication.verifyLogin(nullToken));

        // then
        assertEquals("인증되지 않은 사용자입니다", exception.getMessage());
    }
}