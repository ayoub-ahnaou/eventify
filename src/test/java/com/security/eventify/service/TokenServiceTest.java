package com.security.eventify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.eventify.model.entity.Token;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.TokenRepository;
import com.security.eventify.service.implementation.TokenServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    TokenRepository tokenRepository;

    @InjectMocks
    TokenServiceImpl tokenService;

    @Test
    @DisplayName("generate tocken test method")
    void itShouldReturnGeneratedTocken() {
        // given
        // user
        User user = new User();
        user.setId(1L);
        user.setName("name1");
        user.setEmail("email@gmail.com");
        user.setPassword("pass1234");
        user.setRole(Role.ROLE_USER);

        when(passwordEncoder.encode(anyString())).thenReturn("hashedcode1234");

        when(tokenRepository.save(any(Token.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // when
        String tokenRes = tokenService.generateToken(user);
        // then
        assertNotNull(tokenRes);
        assertNotEquals("hashedcode1234", tokenRes);

        verify(passwordEncoder).encode(anyString());
        verify(tokenRepository).save(any());
    }


    @Test
    @DisplayName("validate token test method")
    void itShouldValidateToken() {
        // given
        String rowToken = "rowtoken123";

        User user = new User();
        user.setId(1L);

        Token validToken = Token.builder()
            .value("hashed123")
            .user(user)
            .expiresAt(LocalDateTime.now().plusDays(1))
            .revoked(false)
            .build();

        Token expiredToken = Token.builder()
            .value("hashedExpired")
            .user(user)
            .expiresAt(LocalDateTime.now().minusDays(1))
            .revoked(false)
            .build();

        Token revokedToken = Token.builder()
            .value("hashedRevoked")
            .user(user)
            .expiresAt(LocalDateTime.now().plusDays(1))
            .revoked(true)
            .build();
        
        when(tokenRepository.findAll()).thenReturn(List.of(validToken, expiredToken, revokedToken));
        when(passwordEncoder.matches(rowToken, "hashed123")).thenReturn(true);

        // when
        Optional<Token> token = tokenService.validate(rowToken);

        // then
        assertTrue(token.isPresent());
        assertEquals(validToken, token.get());

        verify(tokenRepository).findAll();
        verify(passwordEncoder).matches(rowToken, "hashed123");
    }

    @Test
    @DisplayName("revoke test method")
    void itShouldRevokeTokn() {
        // given
        // Token
        User user = new User();
        user.setId(1L);
        Token token = new Token();
        token.setId(1L);
        token.setValue("hashed123");
        token.setUser(user); 
        token.setExpiresAt(LocalDateTime.now().plusDays(1));
        token.setRevoked(false);
        Token.builder();
        // when
        tokenService.revoke(token);
        // then
        assertTrue(token.isRevoked());

        verify(tokenRepository).save(any(Token.class));


    }

    @Test
    @DisplayName("delete token test method")
    void itShouldDeleteToken() {
        // given
        // token 
        Token token = new Token();
        token.setId(1L);
        token.setValue("hashed123");

        when(tokenRepository.findByValue(token.getValue())).thenReturn(Optional.of(token));
        // when
        tokenService.deleteToken(token.getValue());
        // then
        verify(tokenRepository).findByValue(token.getValue());
        verify(tokenRepository).delete(token);
    }

    @Test
    @DisplayName("error delete token test method")
    void itShouldNotDeleteToken() {
        // given
        String value = "hashed123";

        when(tokenRepository.findByValue(value)).thenReturn(Optional.empty());
        // when
        tokenService.deleteToken(value);
        // then
        verify(tokenRepository).findByValue(value);
        verify(tokenRepository, never()).delete(any());
    }

    @Test
    @DisplayName("get user from token test method")
    void itShouldGetUserFromTocken() {
        // given
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setId(1L);

        Token token = Token.builder()
            .id(1L)
            .value("token123")
            .expiresAt(LocalDateTime.now().plusDays(1))
            .user(user)
            .build();
        when(tokenRepository.findAll()).thenReturn(List.of(token));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        // when
        User userRes = tokenService.getUserFromToken("token123").orElse(null);
        // then

        assertNotNull(userRes);
        assertEquals(user.getEmail(), userRes.getEmail());

        verify(tokenRepository).findAll();
        verify(passwordEncoder).matches(anyString(),anyString());

    }
    
}
