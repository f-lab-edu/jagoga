package com.project.jagoga.accommodation.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.jagoga.accommodation.infrastructure.MemoryAccommodationRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.exception.accommodation.DuplicatedAccommodationException;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.infrastructure.MemoryUserRepository;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    private UserRepository userRepository = new MemoryUserRepository();
    private AccommodationRepository accommodationRepository = new MemoryAccommodationRepository();
    private AccommodationService accommodationService
        = new AccommodationService(accommodationRepository, userRepository);

    private String email;
    private String name;
    private String phone;
    private String password;

    private UserCreateRequestDto userCreateRequestDto;
    private User user;
    private AuthUser authUser;

    @BeforeEach
    public void init() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";

        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        user = userRepository.save(userCreateRequestDto.toEntity());
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), user.getRole());
    }

    @AfterEach
    public void after() {
        userRepository.deleteAll();
        accommodationRepository.deleteAll();
    }

    @DisplayName("정상적으로 숙소 등록 시 id가 생성된다.")
    @Test
    void saveAccommodation_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        // when
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        // then
        assertThat(accommodationId)
                .isEqualTo(accommodationRepository.findById(accommodationId).get().getAccommodationId());
    }

    @DisplayName("중복된 숙소명이 있을 경우 등록 시 예외가 발생한다.")
    @Test
    void saveAccommodation_Exception() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        // when
        accommodationService.saveAccommodation(accommodation, authUser);

        // then
        assertThrows(DuplicatedAccommodationException.class,
                () -> accommodationService.saveAccommodation(accommodation, authUser));
    }

    @DisplayName("숙소 정보를 수정한다.")
    @Test
    void updateAccommodation_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        AccommodationRequestDto accommodation2 = AccommodationFactory.mockUpdatedAccommodationRequestDto();

        // when
        Accommodation updatedAccommodation
            = accommodationService.updateAccommodation(
                savedAccommodation.getAccommodationId(), accommodation2, authUser);

        // then
        Optional<Accommodation> findAccommodation
                = accommodationRepository.findById(updatedAccommodation.getAccommodationId());
        assertThat(accommodation.getAccommodationName())
                .isNotEqualTo(findAccommodation.get().getAccommodationName());
    }

    @DisplayName("존재하지 않는 숙소 정보를 수정한다.")
    @Test
    void updateNotExistAccommodation_Success() {
        // given
        AccommodationRequestDto accommodationRequestDto = AccommodationFactory.mockAccommodationRequestDto();

        // then
        assertThrows(NotExistAccommodationException.class,
                () -> accommodationService.updateAccommodation(2L, accommodationRequestDto, authUser));
    }

    @DisplayName("숙소 관리자가 아닌 유저가 숙소 정보를 수정할 경우 예외가 발생한다.")
    @Test
    void updateAccommodationByNotOwner_Exception() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);

        AccommodationRequestDto accommodation2 = AccommodationFactory.mockUpdatedAccommodationRequestDto();
        AuthUser anotherAuthUser = AuthUser.createInstance(2L, "test2@gmail.com", Role.OWNER);

        // then
        assertThrows(ForbiddenException.class,
            () -> accommodationService.updateAccommodation(
                savedAccommodation.getAccommodationId(), accommodation2, anotherAuthUser));
    }

    @DisplayName("숙소 정보를 삭제한다.")
    @Test
    void delete_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        // when
        accommodationService.deleteAccommodation(accommodationId, authUser);

        // then
        assertThrows(NotExistAccommodationException.class,
                () -> accommodationService.getAccommodation(accommodationId));
    }

    @DisplayName("숙소 관리자가 아닌 유저가 숙소를 삭제할 경우 예외가 발생한다.")
    @Test
    void deleteAccommodationByNotOwner_Exception() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        AuthUser anotherAuthUser = AuthUser.createInstance(100000L, "test2@gmail.com", Role.OWNER);

        // then
        assertThrows(ForbiddenException.class,
            () -> accommodationService.deleteAccommodation(accommodationId, anotherAuthUser));
    }

    @DisplayName("관리자는 숙소 정보를 수정할 수 있다.")
    @Test
    void updateAccommodationByAdmin_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        AccommodationRequestDto accommodation2 = AccommodationFactory.mockUpdatedAccommodationRequestDto();
        AuthUser anotherAuthUser = AuthUser.createInstance(2L, "admin@gmail.com", Role.ADMIN);

        // when
        Accommodation updatedAccommodation
            = accommodationService.updateAccommodation(accommodationId, accommodation2, anotherAuthUser);

        // then
        Accommodation findAccommodation
            = accommodationService.getAccommodation(updatedAccommodation.getAccommodationId());
        Assertions.assertThat(findAccommodation.getAccommodationName())
            .isEqualTo(accommodation2.getAccommodationName());
    }

    @DisplayName("관리자는 숙소 정보를 삭제할 수 있다.")
    @Test
    void deleteAccommodationByAdmin_Success() {
        // given
        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto();

        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        Long accommodationId = savedAccommodation.getAccommodationId();

        AuthUser anotherAuthUser = AuthUser.createInstance(2L, "test2@gmail.com", Role.ADMIN);

        // when
        accommodationService.deleteAccommodation(accommodationId, anotherAuthUser);

        // then
        assertThrows(NotExistAccommodationException.class,
            () -> accommodationService.getAccommodation(accommodationId));
    }
}
