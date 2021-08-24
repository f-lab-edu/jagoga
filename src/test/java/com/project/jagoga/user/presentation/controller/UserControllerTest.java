package com.project.jagoga.user.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jagoga.user.application.impl.UserServiceImpl;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    ObjectMapper objectMapper;

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

    @Test
    @DisplayName("사용자 로그인 테스트")
    public void login() throws Exception {
        // given
        userService.signUp(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        String loginJson = objectMapper.writeValueAsString(loginRequestDto);

        // when then
        mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(Matchers.containsString("accessToken")));
    }
}