package com.project.jagoga.roominventory.application;

import com.project.jagoga.exception.roominventory.DuplicatedInventoryException;
import com.project.jagoga.exception.roominventory.InventoryCountNegativeConstraintException;
import com.project.jagoga.exception.roominventory.NotExistInventoryException;
import com.project.jagoga.roominventory.domain.RoomInventory;
import com.project.jagoga.roominventory.domain.RoomInventoryRepository;
import com.project.jagoga.roominventory.infrastructure.JdbcRoomInventoryRepository;
import com.project.jagoga.roominventory.presentation.dto.RoomInventoryAddRequestDto;
import com.project.jagoga.roominventory.presentation.dto.RoomInventoryUpdateRequestDto;
import com.project.jagoga.roomtype.application.RoomTypeService;
import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.utils.VerificationUtils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomInventoryService {

    private final RoomTypeService roomTypeService;
    private final JdbcRoomInventoryRepository jdbcRoomInventoryRepository;
    private final RoomInventoryRepository roomInventoryRepository;

    public void addInventory(
        long roomTypeId, RoomInventoryAddRequestDto roomInventoryAddRequestDto, AuthUser loginUser
    ) {
        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
        VerificationUtils.verifyOwnerPermission(loginUser, roomType.getOwnerId());

        LocalDate startDate = roomInventoryAddRequestDto.getStartDate();
        LocalDate endDate = roomInventoryAddRequestDto.getEndDate();

        List<LocalDate> dates = Stream.iterate(startDate, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)))
            .collect(Collectors.toList());

        List<RoomInventory> roomInventoryList = new ArrayList<>();
        dates.forEach(date -> {
            RoomInventory instance =
                RoomInventory.createInstance(roomTypeId, date, roomInventoryAddRequestDto.getAvailableCount());
            roomInventoryList.add(instance);
        });

        try {
            jdbcRoomInventoryRepository.batchInsertRoomInventories(roomInventoryList);
        } catch (DuplicateKeyException e) {
            throw new DuplicatedInventoryException();
        }
    }

    public void reduceInventory(List<RoomInventory> roomInventories) {
        jdbcRoomInventoryRepository.batchReduceRoomInventories(roomInventories);
    }

    public List<RoomInventory> getInventories(long roomTypeId, LocalDate checkInDate, LocalDate checkOutDate) {
        return roomInventoryRepository.findByRoomTypeIdAndInventoryDateBetween(roomTypeId, checkInDate, checkOutDate);
    }

    public void changeStock(
        long roomTypeId, RoomInventoryUpdateRequestDto roomInventoryUpdateRequestDto, AuthUser loginUser
    ) {
        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
        VerificationUtils.verifyOwnerPermission(loginUser, roomType.getOwnerId());

        LocalDate startDate = roomInventoryUpdateRequestDto.getStartDate();
        LocalDate endDate = roomInventoryUpdateRequestDto.getEndDate();
        int count = roomInventoryUpdateRequestDto.getCount();

        List<RoomInventory> roomInventories =
            jdbcRoomInventoryRepository.batchSelectRoomInventories(roomTypeId, startDate, endDate);
        if (roomInventories.size() != ChronoUnit.DAYS.between(startDate, endDate) + 1) {
            throw new NotExistInventoryException();
        }

        roomInventories.forEach(inventory -> {
            if (inventory.getAvailableCount() + count < 0) {
                throw new InventoryCountNegativeConstraintException();
            }
        });

        jdbcRoomInventoryRepository.batchChangeRoomInventories(roomInventories, count);
    }
}
