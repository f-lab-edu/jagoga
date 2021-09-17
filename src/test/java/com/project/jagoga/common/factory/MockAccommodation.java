package com.project.jagoga.common.factory;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import com.project.jagoga.accommodation.domain.address.City;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MockAccommodation {

    private Long accommodationId;
    private String accommodationName;
    private Long ownerId;
    private String phoneNumber;
    private City city;
    private AccommodationType accommodationType;
    private String description;
    private String information;
    private int lowPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Accommodation build() {
        return new Accommodation(
            accommodationId,
            accommodationName,
            ownerId,
            phoneNumber,
            city,
            accommodationType,
            description,
            information,
            lowPrice
        );
    }
}
