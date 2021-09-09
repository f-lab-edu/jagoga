package com.project.jagoga.user.application.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;

    String email;
    String name;
    String phone;
    String password;
    UserCreateRequestDto userCreateRequestDto;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";
        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
    }

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void signUp() {
        // when
        User user = userService.signUp(userCreateRequestDto);

        // then
        assertNotEquals(password, user.getPassword());
        assertEquals(Role.BASIC, user.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void duplicateUsersignUp() {
        // given
        userService.signUp(userCreateRequestDto);

        // when
        Exception exception =
            assertThrows(DuplicatedUserException.class, () -> userService.signUp(userCreateRequestDto));

        // then
        assertEquals("이미 존재하는 회원입니다", exception.getMessage());
    }

    @Test
    @DisplayName("정상 회원정보 수정 테스트")
    public void updateUser() {
        // given
        User user = userService.signUp(userCreateRequestDto);
        UserUpdateRequestDto userUpdateRequestDto =
            new UserUpdateRequestDto("updatename", "!@#ASkdkdkd", "010-4321-4321");
        AuthUser authUser = AuthUser.createInstance(user.getId(), user.getEmail(), user.getRole());

        // when
        User updateUser = userService.updateUser(user.getId(), userUpdateRequestDto, authUser);

        // then
        assertEquals(email, updateUser.getEmail());
        assertEquals(Role.BASIC, updateUser.getRole());
        assertNotEquals(name, updateUser.getName());
        assertNotEquals(password, updateUser.getPassword());
        assertNotEquals(phone, updateUser.getPhone());
    }

    @Test
    @DisplayName("일반 사용자가 타인의 회원정보 수정하는 경우 예외발생")
    public void updateOtherUser() {
        // given
        User user = userService.signUp(userCreateRequestDto);
        UserUpdateRequestDto userUpdateRequestDto =
            new UserUpdateRequestDto("updatename", "!@#ASkdkdkd", "010-4321-4321");

        long otherUserId = 123L;
        assertNotEquals(user.getId(), otherUserId);
        AuthUser authUser = AuthUser.createInstance(otherUserId, user.getEmail(), user.getRole());

        // when
        Exception exception = assertThrows(ForbiddenException.class,
            () -> userService.updateUser(user.getId(), userUpdateRequestDto, authUser));

        // then
        assertEquals("권한이 없는 사용자입니다", exception.getMessage());
    }

    @Test
    @DisplayName("관리자가 타인의 회원정보 수정")
    public void updateOtherUserByAdmin() {
        // given
        User user = userService.signUp(userCreateRequestDto);
        UserUpdateRequestDto userUpdateRequestDto =
            new UserUpdateRequestDto("updatename", "!@#ASkdkdkd", "010-4321-4321");

        long otherUserId = 123L;
        assertNotEquals(user.getId(), otherUserId);
        AuthUser authUser = AuthUser.createInstance(otherUserId, user.getEmail(), Role.ADMIN);

        // when
        User updateUser = userService.updateUser(user.getId(), userUpdateRequestDto, authUser);

        // then
        assertEquals(email, updateUser.getEmail());
        assertEquals(Role.BASIC, updateUser.getRole());
        assertNotEquals(name, updateUser.getName());
        assertNotEquals(password, updateUser.getPassword());
        assertNotEquals(phone, updateUser.getPhone());
    }
}