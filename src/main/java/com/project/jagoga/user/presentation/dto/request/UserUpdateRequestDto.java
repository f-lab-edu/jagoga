package com.project.jagoga.user.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank(message = "이름은 빈 값일 수 없습니다")
    @Length(max = 20, message = "이름은 20자 이내로 입력하세요")
    private String name;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다")
    @Length(max = 20, message = "비밀번호는 20자 이내로 입력하세요")
    private String password;

    @NotBlank(message = "전화번호는 빈 값일 수 없습니다")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "000-0000-0000'과 같은 형식을 맞추어야합니다")
    private String phone;
}
