package com.squadride.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSquadRequest {
    @NotBlank(message = "Squad name is required")
    @Size(min = 2, max = 50, message = "Squad name must be between 2 and 50 characters")
    private String name;
    
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
}