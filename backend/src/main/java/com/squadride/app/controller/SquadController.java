package com.squadride.app.controller;

import com.squadride.app.dto.SquadDTO;
import com.squadride.app.dto.CreateSquadRequest;
import com.squadride.app.service.SquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class SquadController {
    
    private final SquadService squadService;
    
    @GetMapping("/users/{userId}/squads")
    public ResponseEntity<List<SquadDTO>> getUserSquads(@PathVariable Long userId) {
        List<SquadDTO> squads = squadService.findByUserId(userId);
        return ResponseEntity.ok(squads);
    }
    
    @GetMapping("/squads/{id}")
    public ResponseEntity<SquadDTO> getSquadById(@PathVariable Long id) {
        return squadService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/squads")
    public ResponseEntity<SquadDTO> createSquad(
            @RequestParam Long userId,
            @Valid @RequestBody CreateSquadRequest request) {
        try {
            SquadDTO createdSquad = squadService.createSquad(userId, request);
            return ResponseEntity.ok(createdSquad);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/squads/{id}")
    public ResponseEntity<SquadDTO> updateSquad(
            @PathVariable Long id,
            @Valid @RequestBody CreateSquadRequest request) {
        try {
            SquadDTO updatedSquad = squadService.updateSquad(id, request);
            return ResponseEntity.ok(updatedSquad);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/squads/{id}")
    public ResponseEntity<Void> deleteSquad(@PathVariable Long id) {
        try {
            squadService.deleteSquad(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}