package com.project.jagoga.room.domain;

import com.project.jagoga.utils.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @Column(name = "roomtype_id")
    private long roomTypeId;

    private String roomNumber;

    public static Room createInstance(long roomTypeId, String roomNumber) {
        return new Room(roomTypeId, roomNumber);
    }

    private Room(long roomTypeId, String roomNumber) {
        this.roomTypeId = roomTypeId;
        this.roomNumber = roomNumber;
    }
}
