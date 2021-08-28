package com.project.jagoga.common.factory;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import lombok.Builder;

@Builder
public class AccommodationFactory {

    private AccommodationFactory() {
    }

    public static Accommodation accommodation() {
        return createAccommodation(1L, "testAccommodation");
    }

    public static AccommodationRequestDto mockAccommodationRequestDto() {
        return createAccommodation("test",
                "010-27270-4682",
                null,
                AccommodationType.PENSION,
                "description test",
                "information test");
    }

    public static Accommodation createAccommodation(String accommodationName) {
        return MockAccommodation.builder()
                .accommodationName(accommodationName)
                .build().build();
    }

    public static Accommodation createAccommodation(Long accommodationId, String accommodationName) {
        return MockAccommodation.builder()
                .accommodationId(accommodationId)
                .accommodationName(accommodationName)
                .build().build();
    }

    public static AccommodationRequestDto createAccommodation(String accommodationName,
                                                                  String phoneNumber,
                                                                  City city,
                                                                  AccommodationType accommodationType,
                                                                  String description,
                                                                  String information) {
        return AccommodationRequestDto.builder()
                .accommodationName(accommodationName)
                .description(description)
                .information(information)
                .build();
    }
}
