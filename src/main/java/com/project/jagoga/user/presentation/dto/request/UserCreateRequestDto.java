package com.project.jagoga.user.presentation.dto.request;

import com.project.jagoga.user.domain.User;

public class UserCreateRequestDto {

    // TODO : bean validator 의존성 추가 후 반영
    // @NotBlank(message = "이메일은 빈 값일 수 없습니다")
    // @Email(message = "올바른 형식의 이메일 주소어야 합니다")
    private String email;

    // @NotBlank(message = "이름은 빈 값일 수 없습니다")
    private String name;

    // @NotBlank(message = "비밀번호는 빈 값일 수 없습니다")
    private String password;

    // NotBlank(message = "전화번호는 빈 값일 수 없습니다")
    private String phone;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public UserCreateRequestDto(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public User toEntity() {
        return User.createInstance(email, name, password, phone);
    }
}
