package com.security.eventify.dto.event.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String location;

    @NotBlank
    private Integer capacity;
}
