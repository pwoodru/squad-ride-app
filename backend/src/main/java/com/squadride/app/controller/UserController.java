package com.squadride.app.controller;

import com.squadride.app.dto.*;
import com.squadride.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081", "http://localhost:3001"})
public class UserController {
    
    private final UserService userService;

    // CORS for signup endpoint to allow frontend dev server
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000", "http://localhost:8081"})
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            UserDTO created = userService.signup(userDTO);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        boolean authenticated = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username");
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/stats")
    public ResponseEntity<UserStatsDTO> getUserStats(@PathVariable Long id) {
        UserStatsDTO stats = userService.getUserStats(id);
        return ResponseEntity.ok(stats);
    }
    
    @PutMapping("/{id}/balance")
    public ResponseEntity<UserDTO> updateBalance(
            @PathVariable Long id, 
            @Valid @RequestBody BalanceUpdateRequest request) {
        try {
            UserDTO updatedUser = userService.updateBalance(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}