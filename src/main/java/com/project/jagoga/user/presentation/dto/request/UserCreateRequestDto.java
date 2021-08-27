package com.project.jagoga.user.presentation.dto.request;

import com.project.jagoga.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "이메일은 빈 값일 수 없습니다")
    @Email(message = "올바른 형식의 이메일 주소어야 합니다")
    private String email;

    @NotBlank(message = "이름은 빈 값일 수 없습니다")
    @Length(max = 20, message = "이름은 20자 이내로 입력하세요")
    private String name;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다")
    @Length(max = 20, message = "비밀번호는 20자 이내로 입력하세요")
    private String password;

    @NotBlank(message = "전화번호는 빈 값일 수 없습니다")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "000-0000-0000'과 같은 형식을 맞추어야합니다")
    private String phone;

    public User toEntity() {
        return User.createInstance(email, name, password, phone);
    }
}
