package ua.procamp.footballmanager.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;
import ua.procamp.footballmanager.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Repository
@Transactional
public class CustomTeamRepositoryImpl implements CustomTeamRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addNewPlayerToTeam(long teamId, Player player) {
        Team managedTeam = entityManager.createQuery("select t from Team t where t.id=:id", Team.class)
                .setParameter("id", teamId)
                .getSingleResult();
        Long playerId = player.getId();
        if (playerId == null) {
            managedTeam.addPlayer(player);
        } else {
            Player managedPlayer = managedPlayer(playerId);
            managedTeam.addPlayer(managedPlayer);
        }
    }

    @Override
    public void assignCaptainByTeam(long teamId, long playerId) {
        Team managedTeam = entityManager.createQuery("select t from Team t left join fetch t.players where t.id=:id", Team.class)
                .setParameter("id", teamId)
                .getSingleResult();
        Set<Player> players = managedTeam.getPlayers();
        if (containsPlayerId(playerId, players)) {
            Player player = managedPlayer(playerId);
            managedTeam.setCaptain(player);
        } else {
            String message = String.format("Player with id %d is not a player of team %s", playerId, managedTeam.getName());
            throw new EntityNotFoundException(message);
        }
    }

    @Override
    public void removeById(long teamId) {
        Team team = entityManager.find(Team.class, teamId);
        leaveTeamPlayersWithoutWork(team);
    }

    private void leaveTeamPlayersWithoutWork(Team team) {
        if (team != null) {
            team.getPlayers().forEach(player -> player.setTeam(null));
            Player captain = team.getCaptain();
            if (captain != null) {
                captain.setTeam(null);
            }
            entityManager.remove(team);
        }
    }

    private Player managedPlayer(long playerId) {
        Player player = entityManager.find(Player.class, playerId);
        if (player == null) {
            String message = String.format("Player with id %d not found", playerId);
            throw new EntityNotFoundException(message);
        }
        return player;
    }

    private boolean containsPlayerId(long playerId, Set<Player> players) {
        return players.stream().map(Player::getId).anyMatch(id -> id.equals(playerId));
    }
}
