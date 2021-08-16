package com.project.jagoga.common.factory;

import com.project.jagoga.accommodation.domain.Accommodation;
import lombok.Builder;

@Builder
public class AccommodationFactory {

    private AccommodationFactory() {
    }

    public static Accommodation accommodation(Long accommodationId, String accommodationName) {
        return createAccommodation(accommodationId, accommodationName);
    }

    public static Accommodation accommodation() {
        return createAccommodation(null, "testAccommodation");
    }

    public static Accommodation createAccommodation(Long accommodationId, String accommodationName) {
        return MockAccommodation.builder()
                .accommodationId(accommodationId)
                .accommodationName(accommodationName)
                .build().build();
    }
}
