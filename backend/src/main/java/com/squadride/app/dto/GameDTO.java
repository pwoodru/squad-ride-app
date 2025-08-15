package com.squadride.app.dto;

import com.squadride.app.entity.Game.GameStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private String id;
    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private LocalDateTime gameTime;
    private GameStatus status;
    private Integer week;
    private String season;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}