package com.security.eventify.mapper;

import org.mapstruct.Mapper;

import com.security.eventify.dto.user.UserRequestDTO;
import com.security.eventify.dto.user.UserResponseDTO;
import com.security.eventify.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO userToDto(User user);
    User dtoToUser(UserRequestDTO userRequestDTO);
}
