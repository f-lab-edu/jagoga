package com.project.jagoga.accommodation.presentation.dto;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import com.project.jagoga.accommodation.domain.address.City;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.*;

@Getter
@Builder
public class AccommodationResponseDto {

    private String accommodationName;
    private City city;
    private AccommodationType accommodationType;
    private int lowPrice;

    private AccommodationResponseDto() {
    }

    public AccommodationResponseDto(String accommodationName, City city,
                                    AccommodationType accommodationType, int lowPrice) {
        this.accommodationName = accommodationName;
        this.city = city;
        this.accommodationType = accommodationType;
        this.lowPrice = lowPrice;
    }

    public static AccommodationResponseDto of(Accommodation accommodation) {
        return AccommodationResponseDto.builder()
                .accommodationName(accommodation.getAccommodationName())
                .city(accommodation.getCity())
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
