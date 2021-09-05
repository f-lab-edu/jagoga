package com.project.jagoga.accommodation.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.application.impl.UserServiceImpl;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private Authentication authentication;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    private String email;
    private String name;
    private String phone;
    private String password;

    private UserCreateRequestDto userCreateRequestDto;
    private LoginRequestDto loginRequestDto;
    private User user;
    private AuthUser authUser;
    private String token;

    @BeforeEach
    public void init() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";

        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        user = userService.signUp(userCreateRequestDto);
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), user.getRole());
        loginRequestDto = new LoginRequestDto(email, password);
        token = authentication.login(loginRequestDto);
    }

    @AfterEach
    public void after() {
        userRepository.deleteAll();
        accommodationRepository.deleteAll();
    }

    @DisplayName("정상적으로 숙소 등록 시 id가 생성된다.")
    @Test
    void saveAccommodation_Success() throws Exception {
        // given

        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        // then
        mockMvc.perform(post("/api/accommodation")
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(accommodation))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("중복된 숙소명이 있을 경우 등록 시 예외가 발생한다.")
    @Test
    void saveAccommodation_Exception() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        // when
        accommodationService.saveAccommodation(accommodation, authUser);

        // then
        mockMvc.perform(post("/api/accommodation")
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(accommodation))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isConflict());
    }

    @DisplayName("숙소 정보를 수정한다.")
    @Test
    void updateAccommodation_Success() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        AccommodationRequestDto newAccommodation = AccommodationFactory.mockUpdatedAccommodationRequestDto();

        // then
        mockMvc.perform(put("/api/accommodation/" + savedAccommodation.getAccommodationId())
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(newAccommodation))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 숙소 정보를 수정한다.")
    @Test
    void updateNotExistAccommodation_Success() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();
        accommodationService.saveAccommodation(accommodation, authUser);
        AccommodationRequestDto newAccommodationRequestDto = AccommodationFactory.mockUpdatedAccommodationRequestDto();

        // then
        mockMvc.perform(put("/api/accommodation/10000")
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(newAccommodationRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @DisplayName("숙소 관리자가 아닌 유저가 숙소 정보를 수정할 경우 예외가 발생한다.")
    @Test
    void updateAccommodationByNotOwner_Exception() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);

        String newEmail = "test100@gmail.com";
        String newPassword = "1q2w3e4r!";
        String newPhoneNumber = "010-1111-2222";
        String newName = "이순신";

        UserCreateRequestDto userCreateRequestDto =
            new UserCreateRequestDto(newEmail, newName, newPassword, newPhoneNumber);
        userService.signUp(userCreateRequestDto);
        LoginRequestDto loginRequestDto = new LoginRequestDto(newEmail, newPassword);
        String newToken = authentication.login(loginRequestDto);

        AccommodationRequestDto updatedAccommodationRequestDto
            = AccommodationFactory.mockUpdatedAccommodationRequestDto();

        // then
        mockMvc.perform(put("/api/accommodation/" + savedAccommodation.getAccommodationId())
            .header(HttpHeaders.AUTHORIZATION, newToken)
            .content(objectMapper.writeValueAsString(updatedAccommodationRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @DisplayName("숙소 정보를 삭제한다.")
    @Test
    public void delete_Success() throws Exception {
        //given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        //then
        mockMvc.perform(delete("/api/accommodation/" + accommodationId)
            .header(HttpHeaders.AUTHORIZATION, token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("숙소 관리자가 아닌 유저가 숙소를 삭제할 경우 예외가 발생한다.")
    @Test
    void deleteAccommodationByNotOwner_Exception() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        String newEmail = "test100@gmail.com";
        String newPassword = "1q2w3e4r!";
        String newPhoneNumber = "010-1111-2222";
        String newName = "이순신";

        UserCreateRequestDto userCreateRequestDto =
            new UserCreateRequestDto(newEmail, newName, newPassword, newPhoneNumber);
        userService.signUp(userCreateRequestDto);
        LoginRequestDto loginRequestDto = new LoginRequestDto(newEmail, newPassword);
        String newToken = authentication.login(loginRequestDto);

        // then
        mockMvc.perform(delete("/api/accommodation/" + accommodationId)
            .header(HttpHeaders.AUTHORIZATION, newToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isForbidden());
    }
}
