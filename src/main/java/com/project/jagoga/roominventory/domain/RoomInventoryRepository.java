package com.project.jagoga.roominventory.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {

    List<RoomInventory> findByRoomTypeIdAndInventoryDateBetween(Long roomTypeId, LocalDate from, LocalDate to);

    Optional<RoomInventory> findByRoomTypeIdAndInventoryDate(Long roomTypeId, LocalDate date);
}
