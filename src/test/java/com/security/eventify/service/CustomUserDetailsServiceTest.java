package com.security.eventify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.implementation.CustomUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;


    @Test
    @DisplayName("load user by user name (email) test method")
    void itShouldReturnUserDetailsByUserName() {
        // given
        // user
        User user = new User();
        user.setId(1L);
        user.setEmail("email@gmail.com");
        user.setName("user1");
        user.setPassword("hello1234");
        user.setRole(Role.ROLE_USER);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // when
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        // then

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(userRepository).findByEmail(anyString());
    }


    @Test
    @DisplayName("UsernameNotFoundException handle test method")
    void itShouldTHrowUserNameNotFoundException() {
        // given 
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //when & then
        assertThrows(UsernameNotFoundException.class, ()-> customUserDetailsService.loadUserByUsername("emailnotfound@gmail.com"));
    }

    @Test
    @DisplayName("load user By Email test method")
    void itShouldLoadUserByEmail() {
        // given
        // user
        User user = new User();
        user.setId(1L);
        user.setEmail("email@gmail.com");
        user.setName("name");
        user.setPassword("pass1234");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        // when
        User userResult = customUserDetailsService.loadUserByEmail("email@gmail.com");

        // then
        assertNotNull(userResult);
        assertEquals(user.getEmail(), userResult.getEmail());

        verify(userRepository).findByEmail(anyString());
    }
}
