package com.project.jagoga.accommodation.domain.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class State {

    @Id @GeneratedValue
    @Column(name = "state_id")
    private Long id;
    private String name;

    public State(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
