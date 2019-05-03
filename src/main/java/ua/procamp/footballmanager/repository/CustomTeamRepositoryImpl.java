package ua.procamp.footballmanager.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;
import ua.procamp.footballmanager.exception.TeamRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Repository
@Transactional
public class CustomTeamRepositoryImpl implements CustomTeamRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addNewPlayerToTeam(Long teamId, Player player) {
        Team managedTeam = entityManager.createQuery("select t from Team t where t.id=:id", Team.class)
                .setParameter("id", teamId)
                .getSingleResult();
        managedTeam.addPlayer(player);
    }

    @Override
    public void assignCaptainByTeam(Long teamId, Long playerId) {
        Team managedTeam = entityManager.createQuery("select t from Team t left join fetch t.players where t.id=:id", Team.class)
                .setParameter("id", teamId)
                .getSingleResult();
        Set<Player> players = managedTeam.getPlayers();
        if (containsPlayerId(playerId, players)) {
            Player player = entityManager.find(Player.class, playerId);
            managedTeam.setCaptain(player);
        } else {
            String message = String.format("Player with id %d is not a player of team %s", playerId, managedTeam);
            throw new TeamRepositoryException(message);
        }
    }

    private boolean containsPlayerId(Long playerId, Set<Player> players) {
        return players.stream().map(Player::getId).anyMatch(id -> id.equals(playerId));
    }
}
