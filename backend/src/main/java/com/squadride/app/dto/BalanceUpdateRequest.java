package com.squadride.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceUpdateRequest {
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00", message = "Amount must be non-negative")
    private BigDecimal amount;
    
    private String operation; // ADD, SUBTRACT, SET
    private String reason;
}