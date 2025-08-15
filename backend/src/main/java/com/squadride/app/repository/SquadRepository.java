package com.squadride.app.repository;

import com.squadride.app.entity.Squad;
import com.squadride.app.entity.Squad.SquadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {
    List<Squad> findByUserIdAndStatus(Long userId, SquadStatus status);
    List<Squad> findByUserId(Long userId);
    
    @Query("SELECT COUNT(s) FROM Squad s WHERE s.user.id = :userId AND s.status = :status")
    long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") SquadStatus status);
}