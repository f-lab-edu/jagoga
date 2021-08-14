package com.project.jagoga.user.application.impl;

import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void signUp() {
        // given
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encrypt(user.getPassword())).thenReturn("encodedPassword");

        // when
        userService.signUp(user);

        // then
        verify(userRepository).existsByEmail(user.getEmail());
        verify(passwordEncoder).encrypt(password);
        verify(userRepository).save(user);
        assertEquals(user.getPassword(), "encodedPassword");
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void duplicateUsersignUp() {
        // given
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // when
        Exception exception = assertThrows(DuplicatedUserException.class, () -> userService.signUp(user));

        // then
        assertEquals("이미 존재하는 회원입니다", exception.getMessage());
    }
}