package com.security.eventify.service.implementation;

import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.mapper.UserMapper;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse create(CreateUserRequest dto) {
        User user = userMapper.toEntity(dto);
        return this.userMapper.toDto(this.userRepository.save(user));
    }
}
