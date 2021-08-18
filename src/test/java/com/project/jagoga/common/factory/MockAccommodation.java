package com.project.jagoga.common.factory;

import com.project.jagoga.accommodation.domain.Address;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MockAccommodation {

    private Long accommodationId;
    private String accommodationName;
    private String phoneNumber;
    private Address address;
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
                phoneNumber,
                address,
                accommodationType,
                description,
                information,
                lowPrice,
                createdAt,
                updateAt
        );
    }
}
