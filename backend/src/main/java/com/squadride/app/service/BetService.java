package com.squadride.app.service;

import com.squadride.app.dto.BetDTO;
import com.squadride.app.dto.CreateBetRequest;
import com.squadride.app.entity.Bet;
import com.squadride.app.entity.User;
import com.squadride.app.entity.Squad;
import com.squadride.app.repository.BetRepository;
import com.squadride.app.repository.UserRepository;
import com.squadride.app.repository.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BetService {
    
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final SquadRepository squadRepository;
    
    public List<BetDTO> findByUserId(Long userId) {
        return betRepository.findByUserId(userId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public Optional<BetDTO> findById(Long id) {
        return betRepository.findById(id).map(this::convertToDTO);
    }
    
    public BetDTO createBet(Long userId, CreateBetRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if user has sufficient balance
        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        Squad squad = null;
        if (request.getSquadId() != null) {
            squad = squadRepository.findById(request.getSquadId())
                .orElseThrow(() -> new RuntimeException("Squad not found"));
        }
        
        // Calculate potential payout
        BigDecimal potentialPayout = request.getAmount().multiply(request.getOdds());
        
        Bet bet = new Bet();
        bet.setGameId(request.getGameId());
        bet.setTeamName(request.getTeamName());
        bet.setAmount(request.getAmount());
        bet.setOdds(request.getOdds());
        bet.setBetType(request.getBetType());
        bet.setStatus(Bet.BetStatus.PENDING);
        bet.setPotentialPayout(potentialPayout);
        bet.setUser(user);
        bet.setSquad(squad);
        bet.setCreatedAt(LocalDateTime.now());
        bet.setUpdatedAt(LocalDateTime.now());
        
        // Deduct bet amount from user balance
        user.setBalance(user.getBalance().subtract(request.getAmount()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        
        Bet savedBet = betRepository.save(bet);
        return convertToDTO(savedBet);
    }
    
    public BetDTO updateBetStatus(Long betId, Bet.BetStatus status, BigDecimal actualPayout) {
        Bet bet = betRepository.findById(betId)
            .orElseThrow(() -> new RuntimeException("Bet not found"));
        
        bet.setStatus(status);
        bet.setActualPayout(actualPayout);
        bet.setUpdatedAt(LocalDateTime.now());
        
        // If bet won, add payout to user balance
        if (status == Bet.BetStatus.WON && actualPayout != null) {
            User user = bet.getUser();
            user.setBalance(user.getBalance().add(actualPayout));
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
        
        Bet savedBet = betRepository.save(bet);
        return convertToDTO(savedBet);
    }
    
    private BetDTO convertToDTO(Bet bet) {
        BetDTO dto = new BetDTO();
        dto.setId(bet.getId());
        dto.setGameId(bet.getGameId());
        dto.setTeamName(bet.getTeamName());
        dto.setAmount(bet.getAmount());
        dto.setOdds(bet.getOdds());
        dto.setBetType(bet.getBetType());
        dto.setStatus(bet.getStatus());
        dto.setPotentialPayout(bet.getPotentialPayout());
        dto.setActualPayout(bet.getActualPayout());
        dto.setUserId(bet.getUser().getId());
        dto.setSquadId(bet.getSquad() != null ? bet.getSquad().getId() : null);
        dto.setCreatedAt(bet.getCreatedAt());
        dto.setUpdatedAt(bet.getUpdatedAt());
        return dto;
    }
}