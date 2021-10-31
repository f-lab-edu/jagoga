package com.project.jagoga.roominventory.domain;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {

    List<RoomInventory> findByRoomTypeIdAndInventoryDateBetween(Long roomTypeId, LocalDate from, LocalDate to);
}
