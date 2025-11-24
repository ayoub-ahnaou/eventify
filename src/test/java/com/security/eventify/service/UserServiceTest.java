package com.security.eventify.service;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.request.UpdateRoleRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.mapper.UserMapper;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.implementation.UserServiceImpl;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("create User tese method")
    void itShouldReturnUserAfterCreation() {
        // given
        CreateUserRequest user = new CreateUserRequest();
        user.setEmail("testEmail1@gmail.com");
        user.setName("testName");
        user.setPassword("pass1234");


        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("testEmail1@gmail.com");
        savedUser.setName("testName");
        savedUser.setPassword("pass1234");
        savedUser.setRole(Role.ROLE_USER);


        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("testEmail1@gmail.com");
        userResponse.setName("testName");
        userResponse.setRole(Role.ROLE_USER);

        when(userRepository.save(savedUser)).thenReturn(savedUser);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userMapper.toDto(savedUser)).thenReturn(userResponse);
        when(userMapper.toEntity(user)).thenReturn(savedUser);
        when(passwordEncoder.encode(anyString())).thenReturn("passwordEncoded");

        // when
        UserResponse userResp = userService.create(user);

        //then
        assertNotNull(userResp);
        assertEquals(user.getEmail(), userResp.getEmail());

        verify(userRepository).save(savedUser);
        verify(userRepository).existsByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("get all users test method")
    void itShouldreturnAllUsers() {
        // given
        User user1 = new User();
        user1.setEmail("email1@gmail.com");
        User user2 = new User();
        user2.setEmail("email2@gmail.com");

        UserResponse userRes1 = new UserResponse();
        userRes1.setEmail("email1@gmail.com");

        UserResponse userRes2 = new UserResponse();
        userRes2.setEmail("email1@gmail.com");

        when(userRepository.findAll()).thenReturn(List.of(user1,user2));
        when(userMapper.toDto(user1)).thenReturn(userRes1);
        when(userMapper.toDto(user2)).thenReturn(userRes2);
        // then
        List<UserResponse> users = userService.getAllUsers();
        // then

        assertNotNull(users);
        assertEquals(userRes2.getEmail(), users.get(1).getEmail());

        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    @DisplayName("update User test method")
    void itShouldReturnTheUserUpdated() {
        // given
        
        User user = new User();
        user.setId(1L);
        user.setRole(Role.ROLE_USER);

        UserResponse userRes = new UserResponse();
        userRes.setRole(Role.ROLE_ORGANIZER);
        
        UpdateRoleRequest role = new UpdateRoleRequest();
        role.setRole(Role.ROLE_ORGANIZER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userRes);
        when(userRepository.save(user)).thenReturn(user);

        // when
        UserResponse userResResult = userService.updateRole(1L, role);
        // then

        assertNotNull(userResResult);
        assertEquals(role.getRole(), userResResult.getRole());

        verify(userRepository).save(user);
        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    @DisplayName("get user by id test method")
    void ItShouldReturnUserAfterFindIt() {
        // given
        UserResponse userRes = new UserResponse();
        userRes.setEmail("email1@gmail.com");
        userRes.setName("user");
        userRes.setRole(Role.ROLE_USER);

        User user = new User();
        user.setId(1L);
        user.setEmail("email1@gmail.com");
        user.setName("user");
        user.setRole(Role.ROLE_USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userRes);
        // when
        UserResponse userResResult = userService.getUserById(1L);
        
        // then

        assertNotNull(userResResult);
        assertEquals(userRes.getEmail(), userResResult.getEmail());

        verify(userRepository).findById(1L);
        verifyNoMoreInteractions(userRepository);
    }
}
