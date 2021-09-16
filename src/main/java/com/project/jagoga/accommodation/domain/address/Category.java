package com.project.jagoga.accommodation.domain.address;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String name;

    protected Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
