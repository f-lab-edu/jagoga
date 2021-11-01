package com.project.jagoga.roominventory.domain;

import com.project.jagoga.utils.BaseTimeEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROOM_INVENTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RoomInventory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roominventory_id")
    private Long id;

    private long roomTypeId;

    private LocalDate inventoryDate;

    private int availableCount;

    public boolean hasAvailableRoom() {
        return availableCount > 0;
    }

    public void book() {
        this.availableCount -= 1;
    }

    public static RoomInventory createInstance(
        long roomTypeId, LocalDate inventoryDate, int availableCount
    ) {
        return new RoomInventory(roomTypeId, inventoryDate, availableCount);
    }

    private RoomInventory(long roomTypeId, LocalDate inventoryDate, int availableCount) {
        this.roomTypeId = roomTypeId;
        this.inventoryDate = inventoryDate;
        this.availableCount = availableCount;
    }
}
