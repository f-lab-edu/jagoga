package com.project.jagoga.accommodation.presentation.dto;

import com.project.jagoga.accommodation.domain.Address;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.*;

@Getter
@Builder
public class AccommodationResponseDto {

    private String accommodationName;
    private Address address;
    private AccommodationType accommodationType;
    private int lowPrice;

    private AccommodationResponseDto() {
    }

    public AccommodationResponseDto(String accommodationName, Address address,
                                    AccommodationType accommodationType, int lowPrice) {
        this.accommodationName = accommodationName;
        this.address = address;
        this.accommodationType = accommodationType;
        this.lowPrice = lowPrice;
    }

    public static AccommodationResponseDto of(Accommodation accommodation) {
        return AccommodationResponseDto.builder()
                .accommodationName(accommodation.getAccommodationName())
                .address(accommodation.getAddress())
                .accommodationType(accommodation.getAccommodationType())
                .lowPrice(accommodation.getLowPrice())
                .build();
    }

    public static List<AccommodationResponseDto> listOf(List<Accommodation> accommodations) {
        return accommodations.stream()
                .map(accommodation -> AccommodationResponseDto.of(accommodation))
                .collect(toList());
    }
}
