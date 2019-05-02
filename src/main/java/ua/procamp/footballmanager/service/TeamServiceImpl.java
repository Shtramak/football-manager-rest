package ua.procamp.footballmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dao.TeamRepository;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private TeamRepository repository;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }

    @Override
    public void update(Team team) {
        Team managedTeam = repository.findById(team.getId()).orElseThrow(NoSuchElementException::new);
        managedTeam.setName(team.getName());
        managedTeam.setCaptain(team.getCaptain());
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Player findCaptainByTeam(Team team) {
        return repository.findCaptain(team.getId()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void addNewPlayerToTeam(Player player, Team team) {
        repository.addNewPlayerToTeam(player, team);
    }

    @Override
    public void assignCaptainByTeam(Player player, Team team) {
        repository.assignCaptainByTeam(player, team);
    }
}
