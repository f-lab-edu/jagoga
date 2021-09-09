package com.project.jagoga.user.domain;

import com.project.jagoga.utils.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String name;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEncodedPassword(String password) {
        this.password = password;
    }

    public void updateUser(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public static User createInstance(String email, String name, String password, String phone) {
        Assert.hasText(email, "이메일이 존재하지 않습니다");
        Assert.hasText(name, "이름이 존재하지 않습니다");
        Assert.hasText(password, "비밀번호가 존재하지 않습니다");
        Assert.hasText(phone, "전화번호가 존재하지 않습니다");

        return new User(email, name, password, phone, getUserDefaultRole());
    }

    private static Role getUserDefaultRole() {
        return Role.BASIC;
    }

    private User(String email, String name, String password, String phone, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    private User(long id, String email, String name, String password, String phone, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }
}
