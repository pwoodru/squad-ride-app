package com.squadride.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String displayName;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private BigDecimal todayChange;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}