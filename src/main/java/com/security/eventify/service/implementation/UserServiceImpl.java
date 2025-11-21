package com.security.eventify.service.implementation;

import com.security.eventify.advice.exeption.EmailAlreadyUsedException;
import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.request.UpdateRoleRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.mapper.UserMapper;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.UserService;
import java.util.List;

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


    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(u -> userMapper.toDto(u)).toList();
    }

    @Override
    public UserResponse updateRole(Long id, UpdateRoleRequest roleRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("there is no user with this id"));
        user.setRole(roleRequest.getRole());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("there is no user with this id"));
        return userMapper.toDto(user);
    }

    
}
