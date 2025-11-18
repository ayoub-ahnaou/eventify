package com.security.eventify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends  JpaRepository<Event, Long>{

    
}