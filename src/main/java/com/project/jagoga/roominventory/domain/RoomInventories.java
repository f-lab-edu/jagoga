package com.project.jagoga.roominventory.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class RoomInventories {

    List<RoomInventory> roomInventoryList;

    public static RoomInventories createInstance(List<RoomInventory> roomInventoryList) {
        return new RoomInventories(roomInventoryList);
    }

    public List<RoomInventory> getRoomInventories(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomInventoryList.stream()
            .filter(i -> i.getInventoryDate().isAfter(checkInDate.minusDays(1))
                && i.getInventoryDate().isBefore(checkOutDate.plusDays(1)))
            .collect(Collectors.toList());
    }

    public boolean isAvailableBooking(LocalDate checkInDate, LocalDate checkOutDate) {
        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;
        long count = getRoomInventories(checkInDate, checkOutDate).stream()
            .filter(RoomInventory::hasAvailableRoom)
            .count();
        return days == count;
    }

    public boolean isAvailableChangeable(LocalDate checkInDate, LocalDate checkOutDate) {
        List<RoomInventory> roomInventories = getRoomInventories(checkInDate, checkOutDate);
        return roomInventories.size() == ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;
    }

    private RoomInventories(List<RoomInventory> roomInventoryList) {
        this.roomInventoryList = roomInventoryList;
    }
}
