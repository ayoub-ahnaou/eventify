package com.security.eventify.mapper;

import org.mapstruct.Mapper;

import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.event.response.EventResponseDTO;
import com.security.eventify.model.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventRequestDTO requestDTO);
    EventResponseDTO toDto(Event event);
}
