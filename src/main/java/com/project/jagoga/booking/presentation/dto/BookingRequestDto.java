package com.project.jagoga.booking.presentation.dto;

import com.project.jagoga.booking.domain.Booking;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "체크인 날짜를 지정해야 합니다.")
    private LocalDate checkInDate;

    @NotNull(message = "체크아웃 날짜를 지정해야 합니다.")
    private LocalDate checkOutDate;

    public Booking toEntity(long userId, long roomTypeId) {
        return Booking.createInstance(userId, roomTypeId, checkInDate, checkOutDate);
    }
}
