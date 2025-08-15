package com.squadride.app.repository;

import com.squadride.app.entity.Game;
import com.squadride.app.entity.Game.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    List<Game> findByStatus(GameStatus status);
    List<Game> findByWeekAndSeason(Integer week, String season);
    List<Game> findByGameTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT g FROM Game g WHERE g.status = :status AND g.gameTime <= :currentTime")
    List<Game> findLiveGames(@Param("status") GameStatus status, @Param("currentTime") LocalDateTime currentTime);
}