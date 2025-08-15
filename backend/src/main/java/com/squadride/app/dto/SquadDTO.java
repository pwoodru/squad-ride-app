package com.squadride.app.dto;

import com.squadride.app.entity.Squad.SquadStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SquadDTO {
    private Long id;
    private String name;
    private String description;
    private SquadStatus status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}