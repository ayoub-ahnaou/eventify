package com.security.eventify.dto.event.response;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String location;

    @NotBlank
    private LocalDateTime dateTime;

    @NotBlank
    private Integer capacity;
}
