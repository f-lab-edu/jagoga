package com.project.jagoga.accommodation.domain.address;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class State {

    @Id @GeneratedValue
    @Column(name = "state_id")
    private Long id;
    private String name;

    protected State() {
    }

    public State(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
