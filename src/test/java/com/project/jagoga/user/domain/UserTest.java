package com.project.jagoga.user.domain;

import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    @DisplayName("유저 객체 생성")
    class CreateUser {

        String email;
        String name;
        String phone;
        String password;

        @BeforeEach
        void setup() {
            email = "test@email.com";
            name = "testname";
            password = "testpassword";
            phone = "010-1234-1234";
        }

        @Test
        @DisplayName("정상 케이스")
        void createUser_Normal() {
            // given
            UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);

            // when
            User user = userCreateRequestDto.toEntity();

            // then
            assertEquals(email, user.getEmail());
            assertEquals(name, user.getName());
            assertEquals(password, user.getPassword());
            assertEquals(phone, user.getPhone());
        }

        @Nested
        @DisplayName("실패 케이스")
        class createUser_FailCases {
            @Nested
            @DisplayName("이메일")
            class userId {
                @Test
                @DisplayName("null")
                void failNullId() {
                    // given
                    email = null;

                    UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);

                    // when
                    Exception exception = assertThrows(Exception.class, userCreateRequestDto::toEntity);

                    // then
                    assertEquals("이메일이 존재하지 않습니다", exception.getMessage());
                }

                @Test
                @DisplayName("빈 문자열")
                void failEmptyEmail() {
                    // given
                    email = "";

                    UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);

                    // when
                    Exception exception = assertThrows(Exception.class, userCreateRequestDto::toEntity);

                    // then
                    assertEquals("이메일이 존재하지 않습니다", exception.getMessage());
                }

                @Test
                @DisplayName("공백 문자열")
                void failBlankEmail() {
                    // given
                    email = "  ";

                    UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);

                    // when
                    Exception exception = assertThrows(Exception.class, userCreateRequestDto::toEntity);

                    // then
                    assertEquals("이메일이 존재하지 않습니다", exception.getMessage());
                }
            }
        }
    }
}