package com.project.jagoga.booking.application;

import com.project.jagoga.booking.domain.Booking;
import com.project.jagoga.booking.domain.BookingRepository;
import com.project.jagoga.booking.presentation.dto.BookingRequestDto;
import com.project.jagoga.exception.booking.NonBookableException;
import com.project.jagoga.roominventory.application.RoomInventoryService;
import com.project.jagoga.roominventory.domain.RoomInventory;
import com.project.jagoga.user.domain.AuthUser;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomInventoryService roomInventoryService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookProduct(long roomTypeId, BookingRequestDto bookingRequestDto, AuthUser loginUser) {
        LocalDate checkInDate = bookingRequestDto.getCheckInDate();
        LocalDate checkOutDate = bookingRequestDto.getCheckOutDate();

        List<RoomInventory> roomInventories =
            roomInventoryService.getInventories(roomTypeId, checkInDate, checkOutDate);

        if (!isAvailableBooking(roomInventories, checkInDate, checkOutDate)) {
            throw new NonBookableException();
        }
        roomInventoryService.reduceInventory(roomInventories);

        Booking booking = bookingRequestDto.toEntity(loginUser.getId(), roomTypeId);
        return bookingRepository.save(booking);
    }

    public boolean isAvailableBooking(
        List<RoomInventory> roomInventories, LocalDate checkInDate, LocalDate checkOutDate)  {
        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        long count = roomInventories.stream().filter(RoomInventory::hasAvailableRoom).count();
        return (days + 1 == count);
    }
}
