package com.project.jagoga.booking.application;

import com.project.jagoga.aop.RoomTypeLock;
import com.project.jagoga.booking.domain.Booking;
import com.project.jagoga.booking.domain.BookingRepository;
import com.project.jagoga.booking.presentation.dto.BookingRequestDto;
import com.project.jagoga.exception.booking.NonBookableException;
import com.project.jagoga.roominventory.application.RoomInventoryService;
import com.project.jagoga.roominventory.domain.RoomInventories;
import com.project.jagoga.user.domain.AuthUser;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@RoomTypeLock
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomInventoryService roomInventoryService;

    @Transactional
    public Booking bookProduct(long roomTypeId, BookingRequestDto bookingRequestDto, AuthUser loginUser) {
        LocalDate checkInDate = bookingRequestDto.getCheckInDate();
        LocalDate checkOutDate = bookingRequestDto.getCheckOutDate();

        RoomInventories roomInventories = RoomInventories
            .createInstance(roomInventoryService.getInventories(roomTypeId, checkInDate, checkOutDate));

        if (!roomInventories.isAvailableBooking(checkInDate, checkOutDate)) {
            throw new NonBookableException();
        }
        roomInventoryService.reduceInventory(roomTypeId, roomInventories.getRoomInventories());

        Booking booking = bookingRequestDto.toEntity(loginUser.getId(), roomTypeId);
        return bookingRepository.save(booking);
    }
}
