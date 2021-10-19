package com.project.jagoga.category.application;

import com.project.jagoga.category.domain.Category;
import com.project.jagoga.category.infrastructure.JpaCategoryRepository;
import com.project.jagoga.category.presentation.dto.request.CategoryCreateRequestDto;
import com.project.jagoga.exception.category.DuplicatedCategoryException;
import com.project.jagoga.exception.category.NotExistCategoryException;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.utils.VerificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final JpaCategoryRepository categoryRepository;

    public Category registerCategory(CategoryCreateRequestDto categoryCreateRequestDto, AuthUser loginUser) {
        VerificationUtils.verifyAdminPermission(loginUser);
        Category category = categoryCreateRequestDto.toEntity();
        validateDuplicateCategory(category);
        return categoryRepository.save(category);
    }

    public Category getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(NotExistCategoryException::new);
    }

    private void validateDuplicateCategory(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName())) {
            throw new DuplicatedCategoryException();
        }
    }
}
