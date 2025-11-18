package com.security.eventify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.eventify.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}