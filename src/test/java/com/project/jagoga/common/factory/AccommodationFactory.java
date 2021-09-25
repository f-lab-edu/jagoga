package com.project.jagoga.common.factory;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import com.project.jagoga.accommodation.domain.Category;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.accommodation.domain.address.State;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import lombok.Builder;

@Builder
public class AccommodationFactory {

    private AccommodationFactory() {
    }

    public static Accommodation accommodation() {
        return createAccommodation(1L, "testAccommodation");
    }

    public static AccommodationRequestDto mockAccommodationRequestDto(City city) {
        return createAccommodation("test",
            "010-1111-4682",
            city,
            AccommodationType.PENSION,
            "description test",
            "information test");
    }

    public static AccommodationRequestDto mockAnotherAccommodationRequestDto(City city) {
        return createAccommodation("test",
            "010-1111-2222",
            city,
            AccommodationType.PENSION,
            "description test",
            "information test");
    }

    public static AccommodationRequestDto mockUpdatedAccommodationRequestDto(City city) {
        return createAccommodation("test12",
            "010-2222-4682",
            city,
            AccommodationType.PENSION,
            "description test1",
            "information test1");
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

    public static AccommodationRequestDto createAccommodation(
        String accommodationName,
        String phoneNumber,
        City city,
        AccommodationType accommodationType,
        String description,
        String information
    ) {
        return AccommodationRequestDto.builder()
            .accommodationName(accommodationName)
            .phoneNumber(phoneNumber)
            .city(city)
            .accommodationType(accommodationType)
            .description(description)
            .information(information)
            .build();
    }
}
