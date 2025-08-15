package com.squadride.app.controller;

import com.squadride.app.dto.GameDTO;
import com.squadride.app.entity.Game;
import com.squadride.app.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class GameController {
    
    private final GameService gameService;
    
    @GetMapping("/games")
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> games = gameService.findAll();
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/games/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable String id) {
        return gameService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/games/status/{status}")
    public ResponseEntity<List<GameDTO>> getGamesByStatus(@PathVariable Game.GameStatus status) {
        List<GameDTO> games = gameService.findByStatus(status);
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/games/week/{week}/season/{season}")
    public ResponseEntity<List<GameDTO>> getGamesByWeekAndSeason(
            @PathVariable Integer week, 
            @PathVariable String season) {
        List<GameDTO> games = gameService.findByWeekAndSeason(week, season);
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/scores/live")
    public ResponseEntity<List<GameDTO>> getLiveScores() {
        List<GameDTO> liveGames = gameService.findLiveGames();
        return ResponseEntity.ok(liveGames);
    }
}