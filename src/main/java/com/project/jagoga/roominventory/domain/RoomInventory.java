package com.project.jagoga.roominventory.domain;

import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.utils.BaseTimeEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROOM_INVENTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomInventory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roominventory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomtype_id")
    private RoomType roomType;

    private LocalDate inventoryDate;

    private int availableCount;

    public static RoomInventory createInstance(
        RoomType roomType, LocalDate inventoryDate, int availableCount
    ) {
        return new RoomInventory(roomType, inventoryDate, availableCount);
    }

    private RoomInventory(RoomType roomType, LocalDate inventoryDate, int availableCount) {
        this.roomType = roomType;
        this.inventoryDate = inventoryDate;
        this.availableCount = availableCount;
    }
}
