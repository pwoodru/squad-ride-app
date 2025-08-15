package com.squadride.app.controller;

import com.squadride.app.dto.BetDTO;
import com.squadride.app.dto.CreateBetRequest;
import com.squadride.app.entity.Bet;
import com.squadride.app.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class BetController {
    
    private final BetService betService;
    
    @GetMapping("/users/{userId}/bets")
    public ResponseEntity<List<BetDTO>> getUserBets(@PathVariable Long userId) {
        List<BetDTO> bets = betService.findByUserId(userId);
        return ResponseEntity.ok(bets);
    }
    
    @GetMapping("/bets/{id}")
    public ResponseEntity<BetDTO> getBetById(@PathVariable Long id) {
        return betService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/bets")
    public ResponseEntity<BetDTO> createBet(
            @RequestParam Long userId,
            @Valid @RequestBody CreateBetRequest request) {
        try {
            BetDTO createdBet = betService.createBet(userId, request);
            return ResponseEntity.ok(createdBet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/bets/{id}/status")
    public ResponseEntity<BetDTO> updateBetStatus(
            @PathVariable Long id,
            @RequestParam Bet.BetStatus status,
            @RequestParam(required = false) BigDecimal actualPayout) {
        try {
            BetDTO updatedBet = betService.updateBetStatus(id, status, actualPayout);
            return ResponseEntity.ok(updatedBet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}