package com.security.eventify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.eventify.model.entity.Event;

public interface EventRepository extends  JpaRepository<Event, Long>{

    
}