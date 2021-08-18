package com.project.jagoga.user.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BCryptPasswordEncoderTest {

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("BCrypt 암호화 테스트")
    public void bCrypt() {
        // given
        String string1 = "abcde";

        // when
        String string2 = bCryptPasswordEncoder.encrypt(string1);

        // then
        assertNotEquals(string1, string2);
    }
}