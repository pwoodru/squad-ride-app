package com.squadride.app.dto;

import com.squadride.app.entity.Bet.BetType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBetRequest {
    @NotBlank(message = "Game ID is required")
    private String gameId;
    
    @NotBlank(message = "Team name is required")
    private String teamName;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @DecimalMin(value = "1.0", message = "Odds must be at least 1.0")
    private BigDecimal odds;
    
    @NotNull(message = "Bet type is required")
    private BetType betType;
    
    private Long squadId;
}