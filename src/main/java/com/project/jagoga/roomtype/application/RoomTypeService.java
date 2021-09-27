package com.project.jagoga.roomtype.application;

import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.roomtype.domain.RoomTypeRepository;
import com.project.jagoga.roomtype.presentation.dto.RoomTypeCreateRequestDto;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.utils.VerificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomTypeService {

    private final AccommodationService accommodationService;
    private final RoomTypeRepository roomTypeRepository;

    public RoomType registerRoomType(RoomTypeCreateRequestDto roomTypeCreateRequestDto, AuthUser loginUser) {
        Accommodation accommodation =
            accommodationService.getAccommodation(roomTypeCreateRequestDto.getAccommodationId());
        VerificationUtils.verifyPermission(loginUser, accommodation.getOwnerId());
        RoomType roomType = roomTypeCreateRequestDto.toEntity();
        return roomTypeRepository.save(roomType);
    }
}
