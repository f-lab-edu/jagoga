package com.project.jagoga.category.presentation.dto.response;

import com.project.jagoga.category.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private long categoryId;

    private String name;

    public static CategoryResponseDto createInstance(Category category) {
        return new CategoryResponseDto(category.getId(), category.getName());
    }

    private CategoryResponseDto(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
