package com.project.jagoga.accommodation.presentation.dto;

import com.project.jagoga.accommodation.domain.Address;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationRequestDto {

    private String accommodationName;
    private String phoneNumber;
    private Address address;
    private AccommodationType accommodationType;
    private String description;
    private String information;

    private AccommodationRequestDto() {
    }

    public AccommodationRequestDto(String accommodationName, String phoneNumber,
                                   Address address, AccommodationType accommodationType,
                                   String description, String information) {
        this.accommodationName = accommodationName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accommodationType = accommodationType;
        this.description = description;
        this.information = information;
    }

    public Accommodation toEntity() {
        return Accommodation.builder()
                .accommodationName(accommodationName)
                .phoneNumber(phoneNumber)
                .address(address)
                .accommodationType(accommodationType)
                .description(description)
                .information(information)
                .build();
    }
}
