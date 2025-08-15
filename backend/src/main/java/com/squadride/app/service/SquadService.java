package com.squadride.app.service;

import com.squadride.app.dto.SquadDTO;
import com.squadride.app.dto.CreateSquadRequest;
import com.squadride.app.entity.Squad;
import com.squadride.app.entity.User;
import com.squadride.app.repository.SquadRepository;
import com.squadride.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SquadService {
    
    private final SquadRepository squadRepository;
    private final UserRepository userRepository;
    
    public List<SquadDTO> findByUserId(Long userId) {
        return squadRepository.findByUserId(userId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public Optional<SquadDTO> findById(Long id) {
        return squadRepository.findById(id).map(this::convertToDTO);
    }
    
    public SquadDTO createSquad(Long userId, CreateSquadRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Squad squad = new Squad();
        squad.setName(request.getName());
        squad.setDescription(request.getDescription());
        squad.setUser(user);
        squad.setStatus(Squad.SquadStatus.ACTIVE);
        squad.setCreatedAt(LocalDateTime.now());
        squad.setUpdatedAt(LocalDateTime.now());
        
        Squad savedSquad = squadRepository.save(squad);
        return convertToDTO(savedSquad);
    }
    
    public SquadDTO updateSquad(Long squadId, CreateSquadRequest request) {
        Squad squad = squadRepository.findById(squadId)
            .orElseThrow(() -> new RuntimeException("Squad not found"));
        
        squad.setName(request.getName());
        squad.setDescription(request.getDescription());
        squad.setUpdatedAt(LocalDateTime.now());
        
        Squad savedSquad = squadRepository.save(squad);
        return convertToDTO(savedSquad);
    }
    
    public void deleteSquad(Long squadId) {
        if (!squadRepository.existsById(squadId)) {
            throw new RuntimeException("Squad not found");
        }
        squadRepository.deleteById(squadId);
    }
    
    private SquadDTO convertToDTO(Squad squad) {
        SquadDTO dto = new SquadDTO();
        dto.setId(squad.getId());
        dto.setName(squad.getName());
        dto.setDescription(squad.getDescription());
        dto.setStatus(squad.getStatus());
        dto.setUserId(squad.getUser().getId());
        dto.setCreatedAt(squad.getCreatedAt());
        dto.setUpdatedAt(squad.getUpdatedAt());
        return dto;
    }
}