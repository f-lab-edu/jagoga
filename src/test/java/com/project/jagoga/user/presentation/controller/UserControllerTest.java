package com.project.jagoga.user.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jagoga.user.application.impl.UserServiceImpl;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserUpdateRequestDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    Authentication authentication;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    String email;
    String name;
    String phone;
    String password;
    UserCreateRequestDto userCreateRequestDto;
    LoginRequestDto loginRequestDto;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";
        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        loginRequestDto = new LoginRequestDto(email, password);
    }

    @Test
    @DisplayName("사용자 로그인 테스트")
    public void login() throws Exception {
        // given
        userService.signUp(userCreateRequestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        String loginJson = objectMapper.writeValueAsString(loginRequestDto);

        // when then
        mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(Matchers.containsString("accessToken")));

        userRepository.deleteAll();
    }

    @Test
    @DisplayName("인증된 사용자에 의한 사용자 정보 변경 테스트")
    public void loginByAuthenticatedUser() throws Exception {
        // given
        User user = userService.signUp(userCreateRequestDto);
        String token = authentication.login(loginRequestDto);

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("updateName", "updatePw", "010-4321-4321");
        String updateUserJson = objectMapper.writeValueAsString(userUpdateRequestDto);

        // when then
        mockMvc.perform(put("/api/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(updateUserJson))
                .andExpect(status().isOk());

        userRepository.deleteAll();
    }

    @Test
    @DisplayName("인증되지 않은 사용자에 의한 사용자 정보 변경 테스트")
    public void loginByUnauthenticatedUser() throws Exception {
        // given
        User user = userService.signUp(userCreateRequestDto);

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("updateName", "updatePw", "010-4321-4321");
        String updateUserJson = objectMapper.writeValueAsString(userUpdateRequestDto);

        // when then
        mockMvc.perform(put("/api/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(updateUserJson))
                .andExpect(status().isUnauthorized());

        userRepository.deleteAll();
    }
}