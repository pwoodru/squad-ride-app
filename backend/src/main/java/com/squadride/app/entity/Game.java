package com.squadride.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    private String id;
    
    @Column(name = "home_team", nullable = false)
    private String homeTeam;
    
    @Column(name = "away_team", nullable = false)
    private String awayTeam;
    
    @Column(name = "home_score")
    private Integer homeScore;
    
    @Column(name = "away_score")
    private Integer awayScore;
    
    @Column(name = "game_time", nullable = false)
    private LocalDateTime gameTime;
    
    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.SCHEDULED;
    
    private Integer week;
    
    private String season;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public enum GameStatus {
        SCHEDULED, LIVE, COMPLETED, CANCELLED, POSTPONED
    }
}