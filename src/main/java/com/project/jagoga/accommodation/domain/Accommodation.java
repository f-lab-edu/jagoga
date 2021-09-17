package com.project.jagoga.accommodation.domain;

import com.project.jagoga.accommodation.domain.address.City;

import com.project.jagoga.utils.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "accommodation_id")
    private Long id;
    private String accommodationName;
    private Long ownerId;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;
    private String description;
    private String information;
    private int lowPrice;

    public Accommodation(Long id, String accommodationName, Long ownerId, String phoneNumber,
                         City city, AccommodationType accommodationType, String description, String information,
                         int lowPrice) {
        this.id = id;
        this.accommodationName = accommodationName;
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.accommodationType = accommodationType;
        this.description = description;
        this.information = information;
        this.lowPrice = lowPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation update(String accommodationName, String phoneNumber,
                       City city, AccommodationType accommodationType,
                       String description, String information) {
        return Accommodation.builder()
                .id(this.getId())
                .accommodationName(accommodationName)
                .phoneNumber(phoneNumber)
                .city(city)
                .accommodationType(accommodationType)
                .description(description)
                .information(information)
                .build();
    }
}
