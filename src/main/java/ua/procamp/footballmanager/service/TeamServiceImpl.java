package ua.procamp.footballmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dao.PlayerRepository;
import ua.procamp.footballmanager.dao.TeamRepository;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Player findCaptainByTeam(Team team) {
        return teamRepository.findCaptain(team.getId()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void addNewPlayerToTeam(Player player, Team team) {
        teamRepository.addNewPlayerToTeam(player, team);
    }

    @Override
    public void assignCaptainByTeam(Player player, Team team) {
        teamRepository.assignCaptainByTeam(player, team);
    }
}
