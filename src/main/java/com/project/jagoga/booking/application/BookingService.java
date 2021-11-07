package com.project.jagoga.booking.application;

import com.project.jagoga.booking.domain.Booking;
import com.project.jagoga.booking.domain.BookingRepository;
import com.project.jagoga.booking.presentation.dto.BookingRequestDto;
import com.project.jagoga.exception.booking.NonBookableException;
import com.project.jagoga.roominventory.application.RoomInventoryService;
import com.project.jagoga.roominventory.domain.RoomInventories;
import com.project.jagoga.roominventory.domain.RoomInventory;
import com.project.jagoga.user.domain.AuthUser;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomInventoryService roomInventoryService;

    @Transactional
    public Booking bookProduct(long roomTypeId, BookingRequestDto bookingRequestDto, AuthUser loginUser) {
        LocalDate checkInDate = bookingRequestDto.getCheckInDate();
        LocalDate checkOutDate = bookingRequestDto.getCheckOutDate();

        List<RoomInventory> allRoomInventories =
            roomInventoryService.getInventories(roomTypeId);

        RoomInventories roomInventories = RoomInventories
            .createInstance(allRoomInventories);

        if (!roomInventories.isAvailableBooking(checkInDate, checkOutDate)) {
            throw new NonBookableException();
        }
        roomInventoryService.reduceInventory(roomInventories.getRoomInventories(checkInDate, checkOutDate));

        Booking booking = bookingRequestDto.toEntity(loginUser.getId(), roomTypeId);
        return bookingRepository.save(booking);
    }
}
