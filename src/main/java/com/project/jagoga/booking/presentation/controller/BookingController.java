package com.project.jagoga.booking.presentation.controller;

import com.project.jagoga.booking.application.BookingService;
import com.project.jagoga.booking.domain.Booking;
import com.project.jagoga.booking.presentation.dto.BookingRequestDto;
import com.project.jagoga.booking.presentation.dto.BookingResponseDto;
import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.RequireLoginUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @LoginCheck
    @PostMapping("/api/roomtypes/{roomTypeId}/bookings")
    public ApiResponse<BookingResponseDto> bookProduct(
        @PathVariable final long roomTypeId,
        @Valid @RequestBody final BookingRequestDto bookingRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        Booking booking = bookingService.bookProduct(roomTypeId, bookingRequestDto, loginUser);
        return ApiResponse.createSuccess(BookingResponseDto.createInstance(booking));
    }
}