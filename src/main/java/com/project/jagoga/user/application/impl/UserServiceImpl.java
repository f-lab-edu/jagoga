package com.project.jagoga.user.application.impl;

import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signUp(UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity();
        validateDuplicatedUser(user);
        user.setEncodedPassword(passwordEncoder.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    private void validateDuplicatedUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedUserException();
        }
    }
}
