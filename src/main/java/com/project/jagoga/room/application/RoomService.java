package com.project.jagoga.room.application;

import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.exception.room.NotExistRoomException;
import com.project.jagoga.room.domain.Room;
import com.project.jagoga.room.domain.RoomRepository;
import com.project.jagoga.room.presentation.dto.RoomCreateRequestDto;
import com.project.jagoga.roomtype.application.RoomTypeService;
import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.utils.VerificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final AccommodationService accommodationService;
    private final RoomTypeService roomTypeService;

    public Room registerRoom(
        long accommodationId,
        long roomtypeId,
        RoomCreateRequestDto roomCreateRequestDto,
        AuthUser loginUser
    ) {
        Accommodation accommodation =
            accommodationService.getAccommodationById(accommodationId);
        VerificationUtils.verifyPermission(loginUser, accommodation.getOwnerId());
        RoomType roomType = roomTypeService.getRoomTypeById(roomtypeId);

        Room room = roomCreateRequestDto.toEntity(roomType.getId());
        return roomRepository.save(room);
    }

    public Room getRoomById(long roomId) {
        return roomRepository.findById(roomId)
            .orElseThrow(NotExistRoomException::new);
    }
}
