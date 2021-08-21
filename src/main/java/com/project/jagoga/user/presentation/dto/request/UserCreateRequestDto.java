package com.project.jagoga.user.presentation.dto.request;

import com.project.jagoga.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "이메일은 빈 값일 수 없습니다")
    @Email(message = "올바른 형식의 이메일 주소어야 합니다")
    private String email;

    @NotBlank(message = "이름은 빈 값일 수 없습니다")
    private String name;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다")
    private String password;

    @NotBlank(message = "전화번호는 빈 값일 수 없습니다")
    private String phone;

    public User toEntity() {
        return User.createInstance(email, name, password, phone);
    }
}
