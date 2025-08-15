package com.squadride.app.service;

import com.squadride.app.dto.GameDTO;
import com.squadride.app.entity.Game;
import com.squadride.app.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
    
    private final GameRepository gameRepository;
    
    public List<GameDTO> findAll() {
        return gameRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public Optional<GameDTO> findById(String id) {
        return gameRepository.findById(id).map(this::convertToDTO);
    }
    
    public List<GameDTO> findByStatus(Game.GameStatus status) {
        return gameRepository.findByStatus(status)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<GameDTO> findByWeekAndSeason(Integer week, String season) {
        return gameRepository.findByWeekAndSeason(week, season)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<GameDTO> findLiveGames() {
        return gameRepository.findLiveGames(Game.GameStatus.LIVE, LocalDateTime.now())
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private GameDTO convertToDTO(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setHomeTeam(game.getHomeTeam());
        dto.setAwayTeam(game.getAwayTeam());
        dto.setHomeScore(game.getHomeScore());
        dto.setAwayScore(game.getAwayScore());
        dto.setGameTime(game.getGameTime());
        dto.setStatus(game.getStatus());
        dto.setWeek(game.getWeek());
        dto.setSeason(game.getSeason());
        dto.setCreatedAt(game.getCreatedAt());
        dto.setUpdatedAt(game.getUpdatedAt());
        return dto;
    }
}