package com.project.jagoga.roomtype.presentation.dto;

import com.project.jagoga.roomtype.domain.RoomType;
import lombok.Getter;

@Getter
public class RoomTypeResponseDto {

    private long accommodationId;

    private String name;

    private String description;

    public static RoomTypeResponseDto createInstance(RoomType roomType) {
        return new RoomTypeResponseDto(roomType.getAccommodationId(), roomType.getName(), roomType.getDescription());
    }

    private RoomTypeResponseDto(long accommodationId, String name, String description) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.description = description;
    }
}
