package com.project.jagoga.roomtype.domain;

import com.project.jagoga.utils.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROOMTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomtype_id")
    private Long id;

    private long accommodationId;

    private String name;

    private String description;

    private int price; // 1박 2일 기준 가격

    private long ownerId;

    public static RoomType createInstance(
        long accommodationId, String name, String description, int price, long ownerId
    ) {
        return new RoomType(accommodationId, name, description, price, ownerId);
    }

    private RoomType(long accommodationId, String name, String description, int price, long ownerId) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ownerId = ownerId;
    }
}
