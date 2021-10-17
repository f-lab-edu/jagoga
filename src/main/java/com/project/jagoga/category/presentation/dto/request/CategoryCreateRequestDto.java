package com.project.jagoga.category.presentation.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class CategoryCreateRequestDto {

    @NotBlank(message = "카테고리명은 빈 값일 수 없습니다.")
    @Length(max = 20, message = "카테고리명은 20자 이내로 입력하세요")
    private String name;
}
