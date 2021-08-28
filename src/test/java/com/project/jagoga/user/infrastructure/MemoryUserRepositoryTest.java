package com.project.jagoga.user.infrastructure;

import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemoryUserRepositoryTest {

    UserRepository userRepository = new MemoryUserRepository();

    User user;
    String email;
    String name;
    String phone;
    String password;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";
        user = User.createInstance(email, name, password, phone);
    }

    @Test
    @DisplayName("사용자를 저장한다.")
    public void save() {
        // when
        userRepository.save(user);

        // then
        assertThat(user.getId()).isGreaterThan(0);
        assertTrue(userRepository.existsByEmail(user.getEmail()));

        userRepository.deleteAll();
    }
}