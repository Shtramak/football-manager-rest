package ua.procamp.footballmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, CustomTeamRepository {
    @Query("SELECT t.captain FROM Team t WHERE t.id=:id")
    Optional<Player> findCaptain(@Param("id") long id);

    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players left join fetch t.captain")
    List<Team> findAll();
}
