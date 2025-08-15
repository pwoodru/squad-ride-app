package com.squadride.app.repository;

import com.squadride.app.entity.Bet;
import com.squadride.app.entity.Bet.BetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByUserIdAndStatus(Long userId, BetStatus status);
    List<Bet> findByUserId(Long userId);
    List<Bet> findBySquadId(Long squadId);
    
    @Query("SELECT COUNT(b) FROM Bet b WHERE b.user.id = :userId AND b.status = :status")
    long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BetStatus status);
    
    @Query("SELECT SUM(b.actualPayout) FROM Bet b WHERE b.user.id = :userId AND b.status = 'WON' AND b.createdAt >= :startDate")
    BigDecimal getTotalWinningsForUserSince(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT SUM(b.amount) FROM Bet b WHERE b.user.id = :userId AND b.createdAt >= :startDate")
    BigDecimal getTotalBetAmountForUserSince(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(b) FROM Bet b WHERE b.user.id = :userId AND b.status = 'WON'")
    long countWonBetsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(b) FROM Bet b WHERE b.user.id = :userId AND b.status IN ('WON', 'LOST')")
    long countCompletedBetsByUserId(@Param("userId") Long userId);
}