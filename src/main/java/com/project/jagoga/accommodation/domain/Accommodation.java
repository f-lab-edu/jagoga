package com.project.jagoga.accommodation.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Accommodation {

    private Long accommodationId;
    private String accommodationName;
    private String phoneNumber;
    private Address address;
    private AccommodationType accommodationType;
    private String description;
    private String information;
    private int lowPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Accommodation() {
    }

    public Accommodation(Long accommodationId, String accommodationName,
                         String phoneNumber, Address address,
                         AccommodationType accommodationType, String description,
                         String information, int lowPrice,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accommodationId = accommodationId;
        this.accommodationName = accommodationName;
        this.phoneNumber = phoneNumber;
        this.address = address;
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
}
