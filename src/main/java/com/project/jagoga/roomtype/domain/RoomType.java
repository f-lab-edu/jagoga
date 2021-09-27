package com.project.jagoga.roomtype.domain;

import com.project.jagoga.utils.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
    @Column(name = "roomtype_id")
    private Long id;

    private long accommodationId;

    private String name;

    private String description;

    public static RoomType createInstance(long accommodationId, String name, String description) {
        return new RoomType(accommodationId, name, description);
    }

    private RoomType(long accommodationId, String name, String description) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.description = description;
    }
}
