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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    private static final long PRODUCT_ID = 1L;

    private AccommodationService accommodationService;
    private AccommodationRepository accommodationRepository;

    @BeforeEach
    public void init() {
        accommodationRepository = new MemoryAccommodationRepository();
        accommodationService = new AccommodationService(accommodationRepository);
    }

    @AfterEach
    public void after() {
        accommodationRepository.deleteAll();
    }

    @DisplayName("정상적으로 숙소 등록 시 id가 생성된다.")
    @Test
    void saveAccommodation_Success() {
        // given
        Accommodation accommodation = AccommodationFactory.accommodation();

        // when
        Long accommodationId = accommodationService.saveAccommodation(accommodation);

        // then
        assertThat(accommodationId)
                .isEqualTo(accommodationRepository.findById(accommodationId).get().getAccommodationId());
    }

    @DisplayName("중복된 숙소명이 있을 경우 등록 시 예외를 반환한다.")
    @Test
    void saveAccommodation_Exception() {
        // given
        Accommodation accommodation = AccommodationFactory.accommodation();

        // when
        accommodationRepository.save(accommodation);

        // then
        assertThrows(DuplicatedAccommodationException.class,
                () -> accommodationService.saveAccommodation(accommodation));
    }

    @DisplayName("숙소 정보를 수정한다.")
    @Test
    void updateAccommodation_Success() {
        // given
        Accommodation accommodation = AccommodationFactory.accommodation();
        accommodationService.saveAccommodation(accommodation);
        AccommodationRequestDto accommodationRequestDto
                = AccommodationFactory.mockAccommodationRequestDto();

        // when
        accommodationService.updateAccommodation(1L, accommodationRequestDto);

        // then
        Optional<Accommodation> findAccommodation
                = accommodationRepository.findById(accommodation.getAccommodationId());
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
                () -> accommodationService.updateAccommodation(2L, accommodationRequestDto));
    }

    @DisplayName("숙소을 삭제한다.")
    @Test
    void delete_Success() {
        // given
        Accommodation accommodation
                = AccommodationFactory.accommodation();
        Accommodation saveAccommodation = accommodationRepository.save(accommodation);
        Long accommodationId = saveAccommodation.getAccommodationId();

        // when
        accommodationService.deleteAccommodation(accommodationId);

        // then
        assertThrows(NotExistAccommodationException.class,
                () -> accommodationService.getAccommodation(accommodationId));
    }
}
