package com.project.jagoga.room.presentation.dto;

import com.project.jagoga.room.domain.Room;
import lombok.Getter;

@Getter
public class RoomResponseDto {

    private long roomTypeId;

    private String roomNumber;

    public static RoomResponseDto createInstance(Room room) {
        return new RoomResponseDto(
            room.getRoomTypeId(),
            room.getRoomNumber()
        );
    }

    private RoomResponseDto(long roomTypeId, String roomNumber) {
        this.roomTypeId = roomTypeId;
        this.roomNumber = roomNumber;
    }
}
