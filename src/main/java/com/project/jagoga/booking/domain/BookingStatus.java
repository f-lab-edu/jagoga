package com.project.jagoga.booking.domain;

public enum BookingStatus {

    BOOKED("예약"), CANCEL("취소");

    private final String title;

    BookingStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}