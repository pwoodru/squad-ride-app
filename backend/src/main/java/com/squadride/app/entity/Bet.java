package com.squadride.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String gameId;
    
    @Column(nullable = false)
    private String teamName;
    
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal odds;
    
    @Enumerated(EnumType.STRING)
    private BetType betType = BetType.MONEYLINE;
    
    @Enumerated(EnumType.STRING)
    private BetStatus status = BetStatus.PENDING;
    
    @Column(name = "potential_payout", precision = 10, scale = 2)
    private BigDecimal potentialPayout;
    
    @Column(name = "actual_payout", precision = 10, scale = 2)
    private BigDecimal actualPayout;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squad_id")
    private Squad squad;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public enum BetType {
        MONEYLINE, SPREAD, OVER_UNDER, PROP
    }
    
    public enum BetStatus {
        PENDING, WON, LOST, CANCELLED, PUSHED
    }
}
