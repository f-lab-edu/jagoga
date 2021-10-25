package com.project.jagoga.booking.domain;

import com.project.jagoga.utils.BaseTimeEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Booking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    private long userId;

    @Column(name = "roomtype_id")
    private long roomTypeId;
    
    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public static Booking createInstance(long userId, long roomTypeId, LocalDate checkInDate, LocalDate checkOutDate) {
        return new Booking(userId, roomTypeId, checkInDate, checkOutDate, BookingStatus.BOOKED);
    }

    private Booking(long userId, long roomTypeId, LocalDate checkInDate, LocalDate checkOutDate,
                   BookingStatus bookingStatus) {
        this.userId = userId;
        this.roomTypeId = roomTypeId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = bookingStatus;
    }
}