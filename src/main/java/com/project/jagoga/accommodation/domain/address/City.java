package com.project.jagoga.accommodation.domain.address;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class City {

    private Long cityId;
    private String name;
    private State state;
    private Category category;

    private City() {
    }

    public City(Long cityId, String name, State state, Category category) {
        this.cityId = cityId;
        this.name = name;
        this.state = state;
        this.category = category;
    }
}
