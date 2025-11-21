package com.security.eventify.service.implementation;

import com.security.eventify.advice.exeption.EmailAlreadyUsedException;
import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.mapper.UserMapper;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest dto) {
        // check for duplicate
        if (this.userRepository.existsByEmail(dto.getEmail()))
            throw new EmailAlreadyUsedException("Email Already Exists");

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // hash password
        user.setRole(Role.ROLE_USER); // set default role: user

        return this.userMapper.toDto(this.userRepository.save(user));
    }
}
