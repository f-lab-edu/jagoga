package com.project.jagoga.accommodation.domain.address;

import lombok.Getter;

@Getter
public class Category {

    private Long categoryId;
    private String name;

    private Category() {
    }

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
