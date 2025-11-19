package com.security.eventify.service;

import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.response.UserResponse;

public interface UserService {
    UserResponse create(CreateUserRequest dto);
}
