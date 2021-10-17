package com.project.jagoga.category.presentation.controller;

import com.project.jagoga.category.application.CategoryService;
import com.project.jagoga.category.domain.Category;
import com.project.jagoga.category.presentation.dto.request.CategoryCreateRequestDto;
import com.project.jagoga.category.presentation.dto.response.CategoryResponseDto;
import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.RequireLoginUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @LoginCheck
    @PostMapping("/api/categories")
    public ApiResponse<CategoryResponseDto> registerCategory(
        @Valid @RequestBody final CategoryCreateRequestDto categoryCreateRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        Category category = categoryService.registerCategory(categoryCreateRequestDto, loginUser);
        return ApiResponse.createSuccess(CategoryResponseDto.createInstance(category));
    }
}
