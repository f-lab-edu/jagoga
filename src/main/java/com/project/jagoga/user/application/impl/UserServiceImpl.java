package com.project.jagoga.domain.user.application;

import com.project.jagoga.domain.user.dao.UserRepository;
import com.project.jagoga.domain.user.dto.UserCreateRequestDto;
import com.project.jagoga.domain.user.dto.UserResponse;
import com.project.jagoga.user.application.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse signUp(UserCreateRequestDto userCreateRequestDto) {
        return userRepository.save(userCreateRequestDto.toEntity());
    }
}
