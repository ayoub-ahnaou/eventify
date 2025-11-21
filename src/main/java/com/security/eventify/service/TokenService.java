package com.security.eventify.service;

import com.security.eventify.model.entity.Token;
import com.security.eventify.model.entity.User;

import java.util.Optional;

public interface TokenService {
    // generate token for a user, return token not hashed yet
    String generateToken(User user);

    // Validate raw token and return associated Token entity if valid and not revoked/expired.
    Optional<Token> validate(String token);

    // Revoke token (mark as revoked).
    void revoke(Token token);
    // Delete token from storage.
    void deleteToken(String token);
    Optional<User> getUserFromToken(String token);
}
