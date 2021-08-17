package com.project.jagoga.user.application.impl;

import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.infrastructure.BCryptPasswordEncoder;
import com.project.jagoga.user.infrastructure.MemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    User user;
    String email;
    String name;
    String phone;
    String password;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "testpassword";
        phone = "testphone";
        user = User.createInstance(email, name, password, phone);
    }

    UserRepository userRepository = new MemoryUserRepository();

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder);

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void signUp() {
        // when
        userService.signUp(user);

        // then
        assertNotEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void duplicateUsersignUp() {
        // given
        userService.signUp(user);

        // when
        Exception exception = assertThrows(DuplicatedUserException.class, () -> userService.signUp(user));

        // then
        assertEquals("이미 존재하는 회원입니다", exception.getMessage());
    }
}