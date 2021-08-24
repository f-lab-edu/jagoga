package com.project.jagoga.user.infrastructure;

import com.project.jagoga.exception.user.UnAuthorizedException;
import com.project.jagoga.user.application.impl.UserServiceImpl;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
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

    User user;
    String email;
    String name;
    String phone;
    String password;

    @BeforeEach
    public void init() {
        email = "test1223@test";
        name = "testname";
        password = "testpassword";
        phone = "testphone";
        user = User.createInstance(email, name, password, phone);
    }

    @Test
    @DisplayName("로그인 이후에 발행 된 정상토큰에 대한 검증")
    public void verifyNormalToken() {
        // when
        userService.signUp(user);
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
}