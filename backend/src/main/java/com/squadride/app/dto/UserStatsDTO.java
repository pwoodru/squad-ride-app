package com.squadride.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
    private int activeSquads;
    private int liveBets;
    private int winRate;
    private BigDecimal totalWinnings;
    private BigDecimal totalBetAmount;
    private int totalBets;
    private int wonBets;
    private int lostBets;
}