package com.project.jagoga.booking.presentation.dto;

import com.project.jagoga.booking.domain.Booking;
import com.project.jagoga.booking.domain.BookingStatus;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class BookingResponseDto {

    private long userId;

    private long roomTypeId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String bookingStatus;

    public static BookingResponseDto createInstance(Booking booking) {
        return new BookingResponseDto(booking.getUserId(), booking.getRoomTypeId(),
            booking.getCheckInDate(), booking.getCheckOutDate(), booking.getBookingStatus());
    }

    private BookingResponseDto(
        long userId, long roomTypeId, LocalDate checkInDate, LocalDate checkOutDate, BookingStatus bookingStatus) {
        this.userId = userId;
        this.roomTypeId = roomTypeId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = bookingStatus.getTitle();
    }
}
