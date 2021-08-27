package com.project.jagoga.accommodation.domain.address;

import lombok.Getter;

@Getter
public class State {

    private Long stateId;
    private String name;

    private State() {
    }

    public State(Long stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }
}
