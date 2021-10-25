package com.project.jagoga.booking.application;

import com.project.jagoga.booking.domain.Booking;
import com.project.jagoga.booking.domain.BookingRepository;
import com.project.jagoga.booking.presentation.dto.BookingRequestDto;
import com.project.jagoga.exception.booking.NonBookableException;
import com.project.jagoga.roominventory.application.RoomInventoryService;
import com.project.jagoga.user.domain.AuthUser;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomInventoryService roomInventoryService;

    public Booking bookProduct(long roomTypeId, BookingRequestDto bookingRequestDto, AuthUser loginUser) {
        LocalDate checkInDate = bookingRequestDto.getCheckInDate();
        LocalDate checkOutDate = bookingRequestDto.getCheckOutDate();

        if (!roomInventoryService.isAvailableBooking(roomTypeId, checkInDate, checkOutDate)) {
            throw new NonBookableException();
        }

        Booking booking = bookingRequestDto.toEntity(loginUser.getId(), roomTypeId);
        // TODO : Inventory 줄이기
        return bookingRepository.save(booking);
    }
}
