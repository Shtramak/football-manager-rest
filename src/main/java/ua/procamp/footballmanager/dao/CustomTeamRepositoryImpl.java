package ua.procamp.footballmanager.dao;

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
    public void addNewPlayerToTeam(Player player, Team team) {
        Team managedTeam = entityManager.createQuery("select t from Team t where t.id=:id", Team.class)
                .setParameter("id", team.getId())
                .getSingleResult();
        managedTeam.addPlayer(player);
    }

    @Override
    public void assignCaptainByTeam(Player player, Team team) {
        Team managedTeam = entityManager.createQuery("select t from Team t left join fetch t.players where t.id=:id", Team.class)
                .setParameter("id", team.getId())
                .getSingleResult();
        Set<Player> players = managedTeam.getPlayers();
        if (players.contains(player)) {
            managedTeam.setCaptain(player);
        } else {
            String message = String.format("Player %s is not a player of team %s", player, team);
            throw new TeamRepositoryException(message);
        }
    }
}
