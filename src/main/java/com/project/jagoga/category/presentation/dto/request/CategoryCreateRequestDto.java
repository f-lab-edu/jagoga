package com.project.jagoga.category.presentation.dto.request;

import com.project.jagoga.category.domain.Category;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequestDto {

    @NotBlank(message = "카테고리명은 빈 값일 수 없습니다.")
    @Length(max = 20, message = "카테고리명은 20자 이내로 입력하세요")
    private String name;

    public Category toEntity() {
        return Category.createInstance(name);
    }
}
