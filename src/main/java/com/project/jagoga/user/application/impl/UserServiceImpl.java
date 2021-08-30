package com.project.jagoga.user.application.impl;

import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.exception.user.NotFoundUserException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.*;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserUpdateRequestDto;
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

    @Override
    public User updateUser(long id, UserUpdateRequestDto userUpdateRequestDto, AuthUser loginUser) {
        verifyPermission(loginUser, id);
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        User updateUser = user.updateUser(userUpdateRequestDto.getName(),
                passwordEncoder.encrypt(userUpdateRequestDto.getPassword()),
                userUpdateRequestDto.getPhone());
        return userRepository.update(updateUser);
    }

    private void validateDuplicatedUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedUserException();
        }
    }

    private void verifyPermission(AuthUser loginUser, long userId) {
        if ((loginUser.getRole() != Role.ADMIN) && !loginUser.getId().equals(userId)) {
            throw new ForbiddenException();
        }
    }
}
