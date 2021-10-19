package com.project.jagoga.category.application;

import static com.project.jagoga.user.domain.Role.ADMIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.project.jagoga.category.domain.Category;
import com.project.jagoga.category.presentation.dto.request.CategoryCreateRequestDto;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    String email;
    String name;
    String phone;
    String password;

    UserCreateRequestDto userCreateRequestDto;
    User user;
    AuthUser authUser;

    String categoryName;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";
        categoryName = "testCategoryName";

        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        user = userService.signUp(userCreateRequestDto);
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), ADMIN);
    }

    @Test
    @DisplayName("정상 카테고리 등록시 id가 생성된다.")
    public void registerCategory() {
        // given
        CategoryCreateRequestDto categoryCreateRequestDto =
            new CategoryCreateRequestDto(categoryName);

        // when
        Category category = categoryService.registerCategory(categoryCreateRequestDto, authUser);

        // then
        assertThat(category.getId())
            .isEqualTo(categoryService.getCategoryById(category.getId()).getId());
    }

    @Test
    @DisplayName("일반 사용자가 카테고리 등록시 예외가 발생한다.")
    public void basicUserRegisterCategory_Exception() {
        // given
        CategoryCreateRequestDto categoryCreateRequestDto =
            new CategoryCreateRequestDto(categoryName);

        // when
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), Role.BASIC);

        // then
        assertThrows(ForbiddenException.class,
            () -> categoryService.registerCategory(categoryCreateRequestDto, authUser));
    }

    @Test
    @DisplayName("숙소 사장님이 카테고리 등록시 예외가 발생한다.")
    public void ownerRegisterCategory_Exception() {
        // given
        CategoryCreateRequestDto categoryCreateRequestDto =
            new CategoryCreateRequestDto(categoryName);

        // when
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), Role.OWNER);

        // then
        assertThrows(ForbiddenException.class,
            () -> categoryService.registerCategory(categoryCreateRequestDto, authUser));
    }
}
