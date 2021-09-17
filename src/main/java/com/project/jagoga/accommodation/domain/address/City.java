package com.project.jagoga.accommodation.domain.address;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Builder
@Entity
public class City {

    @Id @GeneratedValue
    @Column(name = "city_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    protected City() {
    }

    public City(Long id, String name, State state, Category category) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.category = category;
    }
}
