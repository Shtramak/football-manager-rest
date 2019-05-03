package ua.procamp.footballmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, CustomTeamRepository {
    @Query("SELECT t.captain from Team t where t.id=:id")
    Optional<Player> findCaptain(@Param("id") Long id);
}
