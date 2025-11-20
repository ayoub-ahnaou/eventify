package com.security.eventify.service;

import java.util.List;

import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.request.UpdateRoleRequest;
import com.security.eventify.dto.user.response.UserResponse;

public interface UserService {
    UserResponse create(CreateUserRequest dto);

    List<UserResponse> getAllUsers();

    UserResponse updateRole(Long id, UpdateRoleRequest roleRequest);

    UserResponse getUserById(Long id);
}
