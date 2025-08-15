package com.squadride.app.dto;

import com.squadride.app.entity.Bet.BetType;
import com.squadride.app.entity.Bet.BetStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetDTO {
    private Long id;
    private String gameId;
    private String teamName;
    private BigDecimal amount;
    private BigDecimal odds;
    private BetType betType;
    private BetStatus status;
    private BigDecimal potentialPayout;
    private BigDecimal actualPayout;
    private Long userId;
    private Long squadId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}