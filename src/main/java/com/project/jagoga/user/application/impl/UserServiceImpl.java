package com.project.jagoga.user.application.impl;

import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
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
    public User signUp(User user) {
        validateDuplicatedUser(user);
        user.setPassword(passwordEncoder.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    private void validateDuplicatedUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedUserException();
        }
    }
}
