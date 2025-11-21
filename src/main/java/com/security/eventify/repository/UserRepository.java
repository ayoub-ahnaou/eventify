package com.security.eventify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.eventify.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
