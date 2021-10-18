package com.project.jagoga.roominventory.application;

import com.project.jagoga.roominventory.domain.RoomInventory;
import com.project.jagoga.roominventory.domain.RoomInventoryRepository;
import com.project.jagoga.roominventory.presentation.dto.RoomInventoryAddRequestDto;
import com.project.jagoga.roomtype.application.RoomTypeService;
import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.utils.VerificationUtils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomInventoryService {

    private final RoomTypeService roomTypeService;
    private final RoomInventoryRepository roomInventoryRepository;

    public void addInventory(
        long roomTypeId, RoomInventoryAddRequestDto roomInventoryAddRequestDto, AuthUser loginUser
    ) {
        RoomType roomType =
            roomTypeService.getRoomTypeById(roomTypeId);
        VerificationUtils.verifyOwnerPermission(loginUser, roomType.getOwnerId());

        LocalDate startDate = roomInventoryAddRequestDto.getStartDate();
        LocalDate endDate = roomInventoryAddRequestDto.getEndDate();

        List<LocalDate> dates = Stream.iterate(startDate, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)))
            .collect(Collectors.toList());

        dates.forEach(date -> {
            RoomInventory instance =
                RoomInventory.createInstance(roomType, date, roomInventoryAddRequestDto.getAvailableCount());
            roomInventoryRepository.save(instance);
        });
    }
}
