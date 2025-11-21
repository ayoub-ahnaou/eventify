package com.security.eventify.service.implementation;

import com.security.eventify.model.entity.Token;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.TokenRepository;
import com.security.eventify.service.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String generateToken(User user) {
        String rawToken = UUID.randomUUID().toString();
        String hashed = passwordEncoder.encode(rawToken);

        Token token = Token.builder()
                .value(hashed)
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();

        tokenRepository.save(token);
        return rawToken;
    }

    @Override
    public Optional<Token> validate(String token) {
        // we must check against all tokens because stored are hashed
        return tokenRepository.findAll().stream()
                .filter(t -> !t.isRevoked())
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .filter(t -> passwordEncoder.matches(token, t.getValue()))
                .findFirst();
    }

    @Override
    public void revoke(Token token) {
        token.setRevoked(true);
        tokenRepository.save(token);
    }

    @Override
    public void deleteToken(String token) {
        tokenRepository.findByValue(token).ifPresent(tokenRepository::delete);
    }

    @Override
    public Optional<User> getUserFromToken(String rawToken) {
        return validate(rawToken).map(Token::getUser);
    }
}
