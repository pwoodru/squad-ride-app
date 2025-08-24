package com.squadride.app.service;

import com.squadride.app.dto.*;
import com.squadride.app.entity.User;
import com.squadride.app.repository.UserRepository;
import com.squadride.app.repository.SquadRepository;
import com.squadride.app.repository.BetRepository;
import com.squadride.app.entity.Squad.SquadStatus;
import com.squadride.app.entity.Bet.BetStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final SquadRepository squadRepository;
    private final BetRepository betRepository;

    // In production, use password hashing (e.g., BCrypt) instead of plain-text comparison!
    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // In production, use password hash comparison!
            return user.getPassword() != null && user.getPassword().equals(password);
        }
        return false;
    }

    public UserDTO signup(UserDTO userDTO) {
        log.info("Attempting signup for email: {} username: {}", userDTO.getEmail(), userDTO.getUsername());
        try {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                log.warn("Signup failed: Email already in use: {}", userDTO.getEmail());
                throw new IllegalArgumentException("Email already in use");
            }
            if (userDTO.getUsername() != null && userRepository.existsByUsername(userDTO.getUsername())) {
                log.warn("Signup failed: Username already in use: {}", userDTO.getUsername());
                throw new IllegalArgumentException("Username already in use");
            }
            User user = new User();
            user.setUsername(userDTO.getUsername() != null ? userDTO.getUsername() : userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setDisplayName(userDTO.getDisplayName());
        user.setBalance(BigDecimal.ZERO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        // user.setPassword(userDTO.getPassword()); // In production, hash this!
        log.info("Saving user: {}", user);
        User saved = userRepository.save(user);
        log.info("User saved with id: {}", saved.getId());
        return convertToDTO(saved);
    } 
    catch (Exception e) {
        log.error("Error during signup for username: {}", userDTO.getUsername(), e);
        throw e;
        }
    }
    
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }
    
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::convertToDTO);
    }
    
    public UserStatsDTO getUserStats(Long userId) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        
        long activeSquads = squadRepository.countByUserIdAndStatus(userId, SquadStatus.ACTIVE);
        long liveBets = betRepository.countByUserIdAndStatus(userId, BetStatus.PENDING);
        
        BigDecimal todayWinnings = betRepository.getTotalWinningsForUserSince(userId, startOfDay);
        BigDecimal todayBetAmount = betRepository.getTotalBetAmountForUserSince(userId, startOfDay);
        
        long wonBets = betRepository.countWonBetsByUserId(userId);
        long completedBets = betRepository.countCompletedBetsByUserId(userId);
        
        int winRate = 0;
        if (completedBets > 0) {
            winRate = (int) Math.round((double) wonBets / completedBets * 100);
        }
        
        BigDecimal todayChange = todayWinnings != null ? todayWinnings : BigDecimal.ZERO;
        if (todayBetAmount != null) {
            todayChange = todayChange.subtract(todayBetAmount);
        }
        
        UserStatsDTO stats = new UserStatsDTO();
        stats.setActiveSquads((int) activeSquads);
        stats.setLiveBets((int) liveBets);
        stats.setWinRate(winRate);
        stats.setTotalWinnings(todayWinnings != null ? todayWinnings : BigDecimal.ZERO);
        stats.setTotalBetAmount(todayBetAmount != null ? todayBetAmount : BigDecimal.ZERO);
        stats.setTotalBets((int) completedBets);
        stats.setWonBets((int) wonBets);
        stats.setLostBets((int) (completedBets - wonBets));
        
        return stats;
    }
    
    public UserDTO updateBalance(Long userId, BalanceUpdateRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        BigDecimal currentBalance = user.getBalance();
        BigDecimal newBalance;
        
        switch (request.getOperation().toUpperCase()) {
            case "ADD":
                newBalance = currentBalance.add(request.getAmount());
                break;
            case "SUBTRACT":
                newBalance = currentBalance.subtract(request.getAmount());
                break;
            case "SET":
                newBalance = request.getAmount();
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + request.getOperation());
        }
        
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        user.setBalance(newBalance);
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setDisplayName(user.getDisplayName());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBalance(user.getBalance());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        
        // Calculate today's change
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        BigDecimal todayWinnings = betRepository.getTotalWinningsForUserSince(user.getId(), startOfDay);
        BigDecimal todayBetAmount = betRepository.getTotalBetAmountForUserSince(user.getId(), startOfDay);
        
        BigDecimal todayChange = todayWinnings != null ? todayWinnings : BigDecimal.ZERO;
        if (todayBetAmount != null) {
            todayChange = todayChange.subtract(todayBetAmount);
        }
        dto.setTodayChange(todayChange);
        
        return dto;
    }
}