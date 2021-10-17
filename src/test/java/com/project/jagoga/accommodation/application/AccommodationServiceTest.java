package com.project.jagoga.accommodation.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.jagoga.category.domain.Category;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.accommodation.domain.address.State;
import com.project.jagoga.category.infrastructure.JpaCategoryRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaCityRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaStateRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.exception.accommodation.DuplicatedAccommodationException;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AccommodationServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    JpaStateRepository jpaStateRepository;

    @Autowired
    JpaCityRepository jpaCityRepository;

    private String email;
    private String name;
    private String phone;
    private String password;

    private UserCreateRequestDto userCreateRequestDto;
    private User user;
    private AuthUser authUser;

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
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), Role.OWNER);

        category = new Category(null, "강릉/경포");
        jpaCategoryRepository.save(category);
        state = new State(null, "강원");
        jpaStateRepository.save(state);
        city = new City(null, "강릉시", state, category.getId());
        jpaCityRepository.save(city);
    }

    @DisplayName("정상적으로 숙소 등록 시 id가 생성된다.")
    @Test
    void saveAccommodation_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        // when
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        // then
        assertThat(accommodationId)
                .isEqualTo(accommodationService.getAccommodationById(accommodationId).getId());
    }

    @DisplayName("일반 사용자가 숙소 등록 시 예외가 발생한다.")
    @Test
    void basicUserSaveAccommodation_Exception() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        // when
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), Role.BASIC);
        // then
        assertThrows(ForbiddenException.class,
            () -> accommodationService.saveAccommodation(accommodation, authUser));
    }

    @DisplayName("중복된 숙소명이 있을 경우 등록 시 예외가 발생한다.")
    @Test
    void saveAccommodation_Exception() {

        //given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        accommodationService.saveAccommodation(accommodation, authUser);

        // when
        AccommodationRequestDto anotherAccommodation = AccommodationFactory.mockAnotherAccommodationRequestDto(city);

        // then
        assertThrows(DuplicatedAccommodationException.class,
                () -> accommodationService.saveAccommodation(anotherAccommodation, authUser));
    }

    @DisplayName("숙소 정보를 수정한다.")
    @Test
    void updateAccommodation_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        AccommodationRequestDto accommodation2 = AccommodationFactory.mockUpdatedAccommodationRequestDto(city);

        // when
        Accommodation updatedAccommodation
            = accommodationService.updateAccommodation(
                savedAccommodation.getId(), accommodation2, authUser);

        // then

        Accommodation findAccommodation = accommodationService.getAccommodationById(updatedAccommodation.getId());
        assertThat(accommodation.getAccommodationName())
                .isNotEqualTo(findAccommodation.getAccommodationName());
    }

    @DisplayName("존재하지 않는 숙소 정보를 수정한다.")
    @Test
    void updateNotExistAccommodation_Success() {
        // given
        AccommodationRequestDto accommodationRequestDto = AccommodationFactory.mockAccommodationRequestDto(city);

        // then
        assertThrows(NotExistAccommodationException.class,
                () -> accommodationService.updateAccommodation(2L, accommodationRequestDto, authUser));
    }

    @DisplayName("숙소 관리자가 아닌 유저가 숙소 정보를 수정할 경우 예외가 발생한다.")
    @Test
    void updateAccommodationByNotOwner_Exception() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);

        AccommodationRequestDto accommodation2 = AccommodationFactory.mockUpdatedAccommodationRequestDto(city);
        AuthUser anotherAuthUser = AuthUser.createInstance(2L, "test2@gmail.com", Role.OWNER);

        // then
        assertThrows(ForbiddenException.class,
            () -> accommodationService.updateAccommodation(
                savedAccommodation.getId(), accommodation2, anotherAuthUser));
    }

    @DisplayName("숙소 정보를 삭제한다.")
    @Test
    void delete_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        // when
        accommodationService.deleteAccommodation(accommodationId, authUser);

        // then
        assertThrows(NotExistAccommodationException.class,
                () -> accommodationService.getAccommodationById(accommodationId));
    }

    @DisplayName("숙소 관리자가 아닌 유저가 숙소를 삭제할 경우 예외가 발생한다.")
    @Test
    void deleteAccommodationByNotOwner_Exception() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        AuthUser anotherAuthUser = AuthUser.createInstance(100000L, "test2@gmail.com", Role.OWNER);

        // then
        assertThrows(ForbiddenException.class,
            () -> accommodationService.deleteAccommodation(accommodationId, anotherAuthUser));
    }

    @DisplayName("관리자는 숙소 정보를 수정할 수 있다.")
    @Test
    void updateAccommodationByAdmin_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        AccommodationRequestDto accommodation2 = AccommodationFactory.mockUpdatedAccommodationRequestDto(city);
        AuthUser anotherAuthUser = AuthUser.createInstance(2L, "admin@gmail.com", Role.ADMIN);

        // when
        Accommodation updatedAccommodation
            = accommodationService.updateAccommodation(accommodationId, accommodation2, anotherAuthUser);

        // then
        Accommodation findAccommodation
            = accommodationService.getAccommodationById(updatedAccommodation.getId());
        Assertions.assertThat(findAccommodation.getAccommodationName())
            .isEqualTo(accommodation2.getAccommodationName());
    }

    @DisplayName("관리자는 숙소 정보를 삭제할 수 있다.")
    @Test
    void deleteAccommodationByAdmin_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getId();

        AuthUser anotherAuthUser = AuthUser.createInstance(2L, "test2@gmail.com", Role.ADMIN);

        // when
        accommodationService.deleteAccommodation(accommodationId, anotherAuthUser);

        // then
        assertThrows(NotExistAccommodationException.class,
            () -> accommodationService.getAccommodationById(accommodationId));
    }
}
