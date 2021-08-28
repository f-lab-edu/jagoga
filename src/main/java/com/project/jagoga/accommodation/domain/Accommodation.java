package com.project.jagoga.accommodation.domain;

import com.project.jagoga.accommodation.domain.address.City;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Accommodation {

    private Long accommodationId;
    private String accommodationName;
    private String phoneNumber;
    private City city;
    private AccommodationType accommodationType;
    private String description;
    private String information;
    private int lowPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Accommodation() {
    }

    public Accommodation(Long accommodationId, String accommodationName,
                         String phoneNumber, City city,
                         AccommodationType accommodationType, String description,
                         String information, int lowPrice,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accommodationId = accommodationId;
        this.accommodationName = accommodationName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.accommodationType = accommodationType;
        this.description = description;
        this.information = information;
        this.lowPrice = lowPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Accommodation update(String accommodationName, String phoneNumber,
                       City city, AccommodationType accommodationType,
                       String description, String information) {
        return Accommodation.builder()
                .accommodationId(this.getAccommodationId())
                .accommodationName(accommodationName)
                .phoneNumber(phoneNumber)
                .city(city)
                .accommodationType(accommodationType)
                .description(description)
                .information(information)
                .build();
    }
}
