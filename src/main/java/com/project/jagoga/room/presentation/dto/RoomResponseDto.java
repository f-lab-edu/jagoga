package com.project.jagoga.room.presentation.dto;

import com.project.jagoga.room.domain.Room;
import com.project.jagoga.room.domain.RoomStatus;
import lombok.Getter;

@Getter
public class RoomResponseDto {

    private long roomTypeId;

    private String roomNumber;

    private RoomStatus roomStatus;

    public static RoomResponseDto createInstance(Room room) {
        return new RoomResponseDto(
            room.getRoomTypeId(),
            room.getRoomNumber(),
            room.getRoomStatus()
        );
    }

    private RoomResponseDto(long roomTypeId, String roomNumber, RoomStatus roomStatus) {
        this.roomTypeId = roomTypeId;
        this.roomNumber = roomNumber;
        this.roomStatus = roomStatus;
    }
}
