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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final SquadRepository squadRepository;
    private final BetRepository betRepository;
    
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