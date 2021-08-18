package com.project.jagoga.accommodation.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.exception.accommodation.DuplicatedAccommodationException;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {
    private static final long PRODUCT_ID = 1L;

    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @DisplayName("정상적으로 상품 등록 시 id가 생성된다.")
    @Test
    void saveAccommodation_Success() {
        // given
        Accommodation accommodation = AccommodationFactory.accommodation();
        when(accommodationRepository.save(any())).thenReturn(accommodation);

        // when
        Long accommodationId = accommodationService.saveAccommodation(accommodation);

        // then
        assertThat(accommodationId).isEqualTo(PRODUCT_ID);
    }

    @DisplayName("중복된 상품명 상품 등록 시 예외를 반환한다.")
    @Test
    void saveAccommodation_Exception() {
        // given
        Accommodation accommodation = AccommodationFactory.accommodation();
        when(accommodationRepository.findByName(accommodation.getAccommodationName())).thenReturn(Optional.of(accommodation));

        // then
        assertThrows(DuplicatedAccommodationException.class, () -> accommodationService.saveAccommodation(accommodation));
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void delete_Success() {
        // given
        Accommodation accommodation = AccommodationFactory.createAccommodation(PRODUCT_ID, "testAccommodation");
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));

        // when
        accommodationService.deleteAccommodation(PRODUCT_ID);

        // then
        verify(accommodationRepository, times(1))
                .delete(PRODUCT_ID);
    }
}