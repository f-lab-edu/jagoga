package com.project.jagoga.accommodation.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import com.project.jagoga.category.domain.Category;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.accommodation.domain.address.State;
import com.project.jagoga.category.infrastructure.JpaCategoryRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaCityRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaStateRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.user.application.impl.UserServiceImpl;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
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
    private UserServiceImpl userService;

    @Autowired
    private Authentication authentication;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    JpaStateRepository jpaStateRepository;

    @Autowired
    JpaCityRepository jpaCityRepository;

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

    private Category category;
    private State state;
    private City city;

    @BeforeEach
    public void init() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";

        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        user = userService.signUp(userCreateRequestDto);
        userService.changeRoleToOwner(user.getId());
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), Role.OWNER);
        loginRequestDto = new LoginRequestDto(email, password);
        token = authentication.login(loginRequestDto);

        category = new Category(null, "??????/??????");
        jpaCategoryRepository.save(category);
        state = new State(null, "??????");
        jpaStateRepository.save(state);
        city = new City(null, "?????????", state, category.getId());
        jpaCityRepository.save(city);
    }

    @AfterEach
    public void after() {
        userRepository.deleteAll();
        accommodationRepository.deleteAll();
    }

    @DisplayName("??????????????? ?????? ????????? ????????????.")
    @Test
    void saveAccommodation_Success() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

        // then
        mockMvc.perform(post("/api/accommodation")
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(accommodation))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("????????? ???????????? ?????? ?????? ?????? ??? ????????? ????????????.")
    @Test
    void saveAccommodation_Exception() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

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

    @DisplayName("?????? ????????? ????????????.")
    @Test
    void updateAccommodation_Success() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        AccommodationRequestDto newAccommodation = AccommodationFactory.mockUpdatedAccommodationRequestDto(city);

        // then
        mockMvc.perform(put("/api/accommodation/" + savedAccommodation.getId())
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(newAccommodation))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("???????????? ?????? ?????? ????????? ????????????.")
    @Test
    void updateNotExistAccommodation_Success() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        accommodationService.saveAccommodation(accommodation, authUser);
        AccommodationRequestDto newAccommodationRequestDto
            = AccommodationFactory.mockUpdatedAccommodationRequestDto(city);

        // then
        mockMvc.perform(put("/api/accommodation/10000")
            .header(HttpHeaders.AUTHORIZATION, token)
            .content(objectMapper.writeValueAsString(newAccommodationRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @DisplayName("?????? ???????????? ?????? ????????? ?????? ????????? ????????? ?????? ????????? ????????????.")
    @Test
    void updateAccommodationByNotOwner_Exception() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);

        String newEmail = "test100@gmail.com";
        String newPassword = "1q2w3e4r!";
        String newPhoneNumber = "010-1111-2222";
        String newName = "?????????";

        UserCreateRequestDto userCreateRequestDto =
            new UserCreateRequestDto(newEmail, newName, newPassword, newPhoneNumber);
        userService.signUp(userCreateRequestDto);
        LoginRequestDto loginRequestDto = new LoginRequestDto(newEmail, newPassword);
        String newToken = authentication.login(loginRequestDto);

        AccommodationRequestDto updatedAccommodationRequestDto
            = AccommodationFactory.mockUpdatedAccommodationRequestDto(city);

        // then
        mockMvc.perform(put("/api/accommodation/" + savedAccommodation.getId())
            .header(HttpHeaders.AUTHORIZATION, newToken)
            .content(objectMapper.writeValueAsString(updatedAccommodationRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @DisplayName("?????? ????????? ????????????.")
    @Test
    public void delete_Success() throws Exception {
        //given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        //then
        mockMvc.perform(delete("/api/accommodation/" + accommodationId)
            .header(HttpHeaders.AUTHORIZATION, token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("?????? ???????????? ?????? ????????? ????????? ????????? ?????? ????????? ????????????.")
    @Test
    void deleteAccommodationByNotOwner_Exception() throws Exception {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        String newEmail = "test100@gmail.com";
        String newPassword = "1q2w3e4r!";
        String newPhoneNumber = "010-1111-2222";
        String newName = "?????????";

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
