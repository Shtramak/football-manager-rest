package ua.procamp.footballmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.procamp.footballmanager.entity.Player;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT t.players FROM Team t WHERE t.id=:id")
    List<Player> findByTeam(@Param("id") Long id);
}
