package com.project.jagoga.user.application.impl;

import com.project.jagoga.exception.user.DuplicatedUserException;
import com.project.jagoga.exception.user.NotFoundUserException;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import com.project.jagoga.user.presentation.dto.request.UserUpdateRequestDto;
import com.project.jagoga.utils.VerificationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        VerificationUtils.verifyBasicPermission(loginUser, id);
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        user.updateUser(userUpdateRequestDto.getName(),
            passwordEncoder.encrypt(userUpdateRequestDto.getPassword()),
            userUpdateRequestDto.getPhone());
        return user;
    }

    @Override
    public User changeRoleToOwner(long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        user.changeRoleToOwner();
        return user;
    }

    private void validateDuplicatedUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedUserException();
        }
    }
}
